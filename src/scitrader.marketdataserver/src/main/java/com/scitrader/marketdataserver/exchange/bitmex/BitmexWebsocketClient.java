package com.scitrader.marketdataserver.exchange.bitmex;


import com.scitrader.marketdataserver.common.MarketDataServerException;
import com.scitrader.marketdataserver.transport.SciTraderWebsocketClient;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class BitmexWebsocketClient implements IBitmexWebsocketClient{

  Logger Log = LogManager.getLogger(BitmexWebsocketClient.class);

  private final String apiUrl = "wss://www.bitmex.com/realtime";
  private final String[] instruments = new String[] { "XBTUSD" };

  public BitmexWebsocketClient() {

  }

  public void connect()  {

    URI uri = getUri();
    Log.info("Initializing SciTraderWebsocketClient with Uri = " + uri.toString());

    SciTraderWebsocketClient ws = new SciTraderWebsocketClient(uri,
            o -> Log.info("Opened websocket"),
            m -> Log.info("Message: " + m),
            (i,s,b) -> Log.info("Socket closed..."),
            ex -> Log.info("Socket error!"));

    Log.info("Starting connection");

    ws.connect();
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
