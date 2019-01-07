package com.scitrader.marketdataserver;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

  private final static Logger log = LogManager.getLogger(Main.class);

  public static void main(String[] args) {

    log.info("I am in your base, killing your doods");

    Injector injector = Guice.createInjector(new MarketDataServerModule());
    MarketDataServer server = injector.getInstance(MarketDataServer.class);

    server.Run();
  }
}

