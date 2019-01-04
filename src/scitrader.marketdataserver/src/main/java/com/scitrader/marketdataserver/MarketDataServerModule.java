package com.scitrader.marketdataserver;

import com.google.inject.AbstractModule;
import com.scitrader.marketdataserver.datastore.IMongoDbService;
import com.scitrader.marketdataserver.datastore.ITickAggregator;
import com.scitrader.marketdataserver.datastore.MongoDbService;
import com.scitrader.marketdataserver.datastore.TickAggregator;
import com.scitrader.marketdataserver.exchange.bitmex.BitmexWebsocketClient;
import com.scitrader.marketdataserver.exchange.bitmex.IBitmexWebsocketClient;

public class MarketDataServerModule extends AbstractModule {

  @Override
  protected void configure() {

    bind(IMarketDataServer.class).to(MarketDataServer.class).asEagerSingleton();
    bind(IBitmexWebsocketClient.class).to(BitmexWebsocketClient.class);
    bind(IMongoDbService.class).to(MongoDbService.class).asEagerSingleton();
    bind(ITickAggregator.class).to(TickAggregator.class).asEagerSingleton();
  }

}
