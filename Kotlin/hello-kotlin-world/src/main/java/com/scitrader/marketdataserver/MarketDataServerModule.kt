package com.scitrader.marketdataserver

import com.google.inject.AbstractModule
import com.scitrader.marketdataserver.datastore.IMongoDbService
import com.scitrader.marketdataserver.datastore.ITickAggregatorService
import com.scitrader.marketdataserver.datastore.MongoDbService
import com.scitrader.marketdataserver.datastore.TickAggregatorService
import com.scitrader.marketdataserver.exchange.bitmex.BitmexWebsocketClient
import com.scitrader.marketdataserver.exchange.bitmex.IBitmexWebsocketClient
import com.scitrader.marketdataserver.exchange.bitmex.messages.BitmexMessageHandler
import com.scitrader.marketdataserver.exchange.bitmex.messages.IBitmexMessageHandler
import com.scitrader.marketdataserver.transport.AutoReconnectWebsocketFactory
import com.scitrader.marketdataserver.transport.IAutoReconnectWebsocketFactory

class MarketDataServerModule : AbstractModule() {

    override fun configure() {
        super.configure()

        bind(IMarketDataServer::class.java).to(MarketDataServer::class.java).asEagerSingleton()
        bind(IMarketDataController::class.java).to(MarketDataController::class.java).asEagerSingleton()
        bind(IBitmexWebsocketClient::class.java).to(BitmexWebsocketClient::class.java).asEagerSingleton()
        bind(IMongoDbService::class.java).to(MongoDbService::class.java).asEagerSingleton()
        bind(ITickAggregatorService::class.java).to(TickAggregatorService::class.java).asEagerSingleton()
        bind(IBitmexMessageHandler::class.java).to(BitmexMessageHandler::class.java)
        bind(IAutoReconnectWebsocketFactory::class.java).to(AutoReconnectWebsocketFactory::class.java)
    }
}