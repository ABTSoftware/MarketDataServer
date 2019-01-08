package com.scitrader.marketdataserver.exchange.bitmex

import com.google.inject.Inject
import com.jsoniter.JsonIterator
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.scitrader.marketdataserver.common.MarketDataServerException
import com.scitrader.marketdataserver.datastore.IMongoDbService
import com.scitrader.marketdataserver.exchange.bitmex.messages.InfoMessage
import com.scitrader.marketdataserver.exchange.bitmex.messages.SubscribeMessage
import com.scitrader.marketdataserver.exchange.bitmex.messages.Tick
import com.scitrader.marketdataserver.exchange.bitmex.messages.TicksMessage
import com.scitrader.marketdataserver.transport.AutoReconnectWebsocket
import org.apache.logging.log4j.LogManager
import org.bson.Document
import java.net.URI
import java.util.concurrent.atomic.AtomicLong

class BitmexWebsocketClient : IBitmexWebsocketClient {

    companion object {
        private val Log = LogManager.getLogger(BitmexWebsocketClient::class.java)
        private val apiUrl = "wss://www.bitmex.com/realtime"
    }

    private var mongoDatabase: MongoDatabase
    private var messageCount : AtomicLong = AtomicLong(0)

    @Inject
    constructor(mongoDbService: IMongoDbService) {

        this.mongoDatabase = mongoDbService.tickDatabase
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

                val mc : Long = messageCount.addAndGet(1)
                if (mc % 100 == 0L){
                    Log.info("($mc th Message) Received data: " + ticks.data)
                }

                for (t in ticks.data) {
                    val collection = getMongoCollection(t)
                    val doc = t.toBsonDocument()
                    collection.insertOne(doc)
                }
            }
        } else {
            throw MarketDataServerException("The message type has no associated deserializer. Message=$message")
        }
    }

    private fun getMongoCollection(t: Tick): MongoCollection<Document> {
        val symbol = t.symbol
        return mongoDatabase.getCollection("BITMEX:$symbol")
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