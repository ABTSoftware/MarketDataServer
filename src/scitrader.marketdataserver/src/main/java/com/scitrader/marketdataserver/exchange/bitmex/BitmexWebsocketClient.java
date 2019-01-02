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

  public BitmexWebsocketClient() {

  }

  public void connect()  {
    SciTraderWebsocketClient ws = new SciTraderWebsocketClient(getUri(),
            a -> Log.info("Socket opened..."),
            m -> Log.info("Message: " + m),
            (i,s,b) -> Log.info("Socket closed..."),
            ex -> Log.info("Socket error!"));

    ws.connect();
  }

  private URI getUri(){
    try{
      return new URI(this.apiUrl);
    }
    catch(Exception ex){
      throw new MarketDataServerException(ex);
    }
  }
}
