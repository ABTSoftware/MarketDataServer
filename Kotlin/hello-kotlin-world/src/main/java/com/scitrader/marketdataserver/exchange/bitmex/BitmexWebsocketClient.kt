package com.scitrader.marketdataserver.exchange.bitmex

import com.jsoniter.JsonIterator
import com.scitrader.marketdataserver.common.MarketDataServerException
import com.scitrader.marketdataserver.exchange.bitmex.messages.InfoMessage
import com.scitrader.marketdataserver.exchange.bitmex.messages.SubscribeMessage
import com.scitrader.marketdataserver.exchange.bitmex.messages.TicksMessage
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
        if (message.contains("info")) {
            val info = JsonIterator.deserialize(message, InfoMessage::class.java)
            Log.info("Received info: " + info.info)

        } else if (message.contains("success")) {
            val sub = JsonIterator.deserialize(message, SubscribeMessage::class.java)
            Log.info("Received Subscribe! " + sub.request!!.args)
        } else if (message.contains("table") && message.contains("trade")) {
            if (message.contains("partial")) {
                Log.info("Ignoring partial trade message: $message")
            } else if (message.contains("insert")) {
                val ticks = JsonIterator.deserialize(message, TicksMessage::class.java)
                Log.info("Received data: " + ticks.data)
//                for (t in ticks.data) {
////                    val collection = getMongoCollection(t)
////                    val doc = t.toBsonDocument()
////                    collection.insertOne(doc)
////                }
            }
        } else {
            throw MarketDataServerException("The message type has no associated deserializer. Message=$message")
        }
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