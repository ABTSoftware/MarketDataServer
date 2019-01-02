package com.scitrader.marketdataserver;

import com.google.inject.AbstractModule;

public class MarketDataServerModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(MarketDataServer.class).to(MarketDataServerImpl.class).asEagerSingleton();
  }

}
