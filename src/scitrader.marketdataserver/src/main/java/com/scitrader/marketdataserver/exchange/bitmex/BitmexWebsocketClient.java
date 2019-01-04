package com.scitrader.marketdataserver.exchange.bitmex;


import com.google.inject.Inject;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.scitrader.marketdataserver.common.Guard;
import com.scitrader.marketdataserver.common.MarketDataServerException;
import com.scitrader.marketdataserver.datastore.IMongoDbService;
import com.scitrader.marketdataserver.transport.SciTraderWebsocketClient;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class BitmexWebsocketClient implements IBitmexWebsocketClient{

  private static final Logger Log = LogManager.getLogger(BitmexWebsocketClient.class);

  private final String apiUrl = "wss://www.bitmex.com/realtime";
  private final String[] instruments = new String[] { "XBTUSD" };
  private final MongoDatabase mongoDatabase;

  @Inject
  public BitmexWebsocketClient(IMongoDbService mongoDbService) {

    Guard.NotNull(mongoDbService, "MongoDbService must not be null");

    MongoClient mongoClient = mongoDbService.getMongoClient();
    this.mongoDatabase = mongoClient.getDatabase("com_scitrader_marketdataserver");
  }

  public void connect()  {

    URI uri = getUri();
    Log.info("Initializing SciTraderWebsocketClient with Uri = " + uri.toString());

    SciTraderWebsocketClient ws = new SciTraderWebsocketClient(uri,
            o -> Log.info("Opened websocket"),
            m -> onMessage(m),
            (i,s,b) -> Log.info("Socket closed..."),
            ex -> Log.error("Socket error!", ex));

    Log.info("Starting connection");

    ws.connect();
  }

  private void onMessage(String message) {
    //Any trade = JsonIterator.deserialize(message);

    if (message.contains("info")) {
      InfoMessage info = JsonIterator.deserialize(message, InfoMessage.class);
      Log.info("Received info: " + info.getInfo());

    } else if (message.contains("success")) {
      SubscribeMessage sub = JsonIterator.deserialize(message, SubscribeMessage.class);
      Log.info("Received Subscribe! " + sub.getRequest().getArgs());
    } else if (message.contains("table") && message.contains("trade")) {
      if (message.contains("partial")){
        Log.info("Ignoring partial trade message: " + message);
      }
      else if (message.contains("insert")){
        TicksMessage ticks = JsonIterator.deserialize(message, TicksMessage.class);
        for(Tick t : ticks.data){
          String symbol = t.getSymbol();
          MongoCollection<Document> collection = mongoDatabase.getCollection(symbol);
          Document doc = t.toBsonDocument();
          collection.insertOne(doc);
        }
        Log.info("Received data: " + ticks.data);
      }
    }
    else {
      throw new MarketDataServerException("The message type has no associated deserializer. Message=" + message);
    }
  }

  private URI getUri(){
    try{
      StringBuilder sb  = new StringBuilder();
      sb.append(apiUrl);
      //sb.append("?subscribe=instrument");
      sb.append("?subscribe=trade:XBTUSD,trade:ETHUSD");
//      for(String inst : instruments){
//        sb.append(",trade:").append(inst);
//      }
      return new URI(sb.toString());
    }
    catch(Exception ex){
      throw new MarketDataServerException(ex);
    }
  }
}
