package com.scitrader.marketdataserver;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.scitrader.marketdataserver.exchange.bitmex.IBitmexWebsocketClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

@Singleton
public class MarketDataServer implements IMarketDataServer{

  Logger Log = LogManager.getLogger(MarketDataServer.class);
  private IBitmexWebsocketClient wsClient;

  @Inject
  public MarketDataServer(IBitmexWebsocketClient wsClient){

    this.wsClient = wsClient;
  }

  @Override
  public synchronized void Run() {

    this.wsClient.connect();

    // Wait
    while(true){
      Log.info("server status ==> " + Calendar.getInstance().getTime());
      try {
        this.wait(2000);
      } catch (InterruptedException e) {

        e.printStackTrace();
      }
    }
  }
}
