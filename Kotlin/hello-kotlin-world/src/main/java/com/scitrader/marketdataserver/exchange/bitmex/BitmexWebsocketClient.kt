package com.scitrader.marketdataserver.exchange.bitmex

import com.scitrader.marketdataserver.common.MarketDataServerException
import com.scitrader.marketdataserver.transport.AutoReconnectWebsocket
import org.apache.logging.log4j.LogManager
import java.net.URI

class BitmexWebsocketClient : IBitmexWebsocketClient {

    companion object {
        private val Log = LogManager.getLogger(BitmexWebsocketClient::class.java)
        private val apiUrl = "wss://www.bitmex.com/realtime"
    }

    override fun connect() {

        val uri = getUri()
        Log.info("Initializing AutoReconnectWebsocket with Uri = " + uri.toString())

        val ws = AutoReconnectWebsocket(uri, { m -> onMessage(m)})
        ws.connect()
    }

    private fun onMessage(message: String) {
        Log.info("Websocket message: " + message)
    }

    private fun getUri(): URI {
        try {
            val sb = StringBuilder()
            sb.append(apiUrl)

            // TODO: Configuration file for instruments, or subscribe to all instruments
            sb.append("?subscribe=trade:XBTUSD,trade:ETHUSD")

            return URI(sb.toString())
        } catch (ex: Exception) {
            throw MarketDataServerException(ex)
        }
    }

}