package com.scitrader.marketdataserver

import com.google.inject.AbstractModule

class MarketDataServerModule : AbstractModule() {

    override fun configure() {
        super.configure()

        bind(IMarketDataServer::class.java).to(MarketDataServer::class.java).asEagerSingleton()
        bind(IMarketDataController::class.java).to(MarketDataController::class.java).asEagerSingleton()
    }
}