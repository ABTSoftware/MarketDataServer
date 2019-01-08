package com.scitrader.marketdataserver

import com.google.inject.Inject
import com.scitrader.marketdataserver.exchange.bitmex.IBitmexWebsocketClient
import org.apache.logging.log4j.LogManager
import java.util.*

class MarketDataServer : IMarketDataServer {

    internal var Log = LogManager.getLogger(MarketDataServer::class.java)

    private var marketDataController: IMarketDataController
    private val syncObj = java.lang.Object()

    private var bitmexClient: IBitmexWebsocketClient

    @Inject
    constructor(bitmexClient : IBitmexWebsocketClient,
                marketDataController: IMarketDataController){

        this.marketDataController = marketDataController
        this.bitmexClient = bitmexClient;
    }

    @Synchronized override fun Run() {
        Log.info("The market data server is aliiiive!")

        this.marketDataController.Init()
        this.bitmexClient.connect()

        // Wait
        while (true) {
            Log.info("server status ==> " + Calendar.getInstance().getTime());
            try {
                synchronized(syncObj) {
                    syncObj.wait(10000)
                }
            } catch (e: InterruptedException) {

                e.printStackTrace()
            }

        }
    }
}