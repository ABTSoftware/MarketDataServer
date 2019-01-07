package com.scitrader.marketdataserver

import com.google.inject.Inject
import org.apache.logging.log4j.LogManager
import java.util.*

class MarketDataServer : IMarketDataServer {

    internal var Log = LogManager.getLogger(MarketDataServer::class.java)

    private var marketDataController: IMarketDataController
    private val syncObj = java.lang.Object()

    //private var wsClient: IBitmexWebsocketClient

    @Inject
    constructor(//wsClient : IBitmexWebsocketClient,
                marketDataController: IMarketDataController){

        this.marketDataController = marketDataController
        //this.wsClient = wsClient;
    }

    @Synchronized override fun Run() {
        Log.info("The market data server is aliiiive!")

        this.marketDataController.Init()

        // Wait
        while (true) {
            Log.info("server status ==> " + Calendar.getInstance().getTime());
            try {
                synchronized(syncObj) {
                    syncObj.wait(2000)
                }
            } catch (e: InterruptedException) {

                e.printStackTrace()
            }

        }
    }
}