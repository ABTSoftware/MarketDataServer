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
        Log.info("Errrmagerrrsh, I'm connecting...")

        val uri = getUri()
        val ws = AutoReconnectWebsocket(uri)
        ws.connect()
    }

    private fun getUri(): URI {
        try {
            val sb = StringBuilder()
            sb.append(apiUrl)
            //sb.append("?subscribe=instrument");
            sb.append("?subscribe=trade:XBTUSD,trade:ETHUSD")
            //      for(String inst : instruments){
            //        sb.append(",trade:").append(inst);
            //      }
            return URI(sb.toString())
        } catch (ex: Exception) {
            throw MarketDataServerException(ex)
        }
    }

}