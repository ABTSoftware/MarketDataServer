package com.scitrader.marketdataserver.transport

import com.scitrader.marketdataserver.common.MarketDataServerException
import org.apache.logging.log4j.LogManager
import java.net.URI

interface IAutoReconnectWebsocket{
    fun connect()
}

public class AutoReconnectWebsocket : IAutoReconnectWebsocket {

    private val Log = LogManager.getLogger(AutoReconnectWebsocket::class.java)

    private var serverUri: URI
    private var isConnected : Boolean = false

    constructor(serverUri : URI)  {
        this.serverUri = serverUri
    }

    override fun connect(){
        Log.info("AutoReconnectWebsocket.connect()")

        if (isConnected){
            throw MarketDataServerException("Socket is already connected")
        }

        val ws = SciTraderWebsocketClient(serverUri,
                { o -> Log.info("Opened websocket") },
                { m -> Log.info("Message: " + m) },
                { i, s, b -> Log.info("Socket closed...") },
                { ex -> Log.error("Socket error!", ex) })
        ws.connect()
    }
}