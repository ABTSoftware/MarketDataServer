package com.scitrader.marketdataserver.exchange.bitmex

import org.apache.logging.log4j.LogManager

class BitmexWebsocketClient : IBitmexWebsocketClient {

    private val Log = LogManager.getLogger(BitmexWebsocketClient::class.java)

    override fun connect() {
        Log.info("Errrmagerrrsh, I'm connecting...")
    }

}