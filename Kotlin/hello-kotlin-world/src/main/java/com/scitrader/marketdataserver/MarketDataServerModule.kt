package com.scitrader.marketdataserver

import com.google.inject.AbstractModule
import com.scitrader.marketdataserver.datastore.IMongoDbService
import com.scitrader.marketdataserver.datastore.MongoDbService
import com.scitrader.marketdataserver.exchange.bitmex.BitmexWebsocketClient
import com.scitrader.marketdataserver.exchange.bitmex.IBitmexWebsocketClient

class MarketDataServerModule : AbstractModule() {

    override fun configure() {
        super.configure()

        bind(IMarketDataServer::class.java).to(MarketDataServer::class.java).asEagerSingleton()
        bind(IMarketDataController::class.java).to(MarketDataController::class.java).asEagerSingleton()
        bind(IBitmexWebsocketClient::class.java).to(BitmexWebsocketClient::class.java).asEagerSingleton()
        bind(IMongoDbService::class.java).to(MongoDbService::class.java).asEagerSingleton()
    }
}