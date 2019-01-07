package com.scitrader.marketdataserver

import org.apache.logging.log4j.LogManager

class MarketDataServer : IMarketDataServer {

    internal var Log = LogManager.getLogger(MarketDataServer::class.java)

    override fun Run() {
        Log.info("The market data server is aliiiive!")
    }

}