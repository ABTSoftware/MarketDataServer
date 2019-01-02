package com.scitrader.marketdataserver;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

public class MarketDataServerImpl implements MarketDataServer{

  Logger Log = LogManager.getLogger(MarketDataServerImpl.class);

  @Inject
  public MarketDataServerImpl(){

  }

  @Override
  public synchronized void Run() {
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
