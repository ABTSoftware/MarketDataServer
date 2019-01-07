package com.scitrader.marketdataserver.transport

import com.scitrader.marketdataserver.common.MarketDataServerException
import org.apache.logging.log4j.LogManager
import java.net.URI

interface IAutoReconnectWebsocket{
    fun connect()
    fun stop()
}

public class AutoReconnectWebsocket : IAutoReconnectWebsocket {

    private val Log = LogManager.getLogger(AutoReconnectWebsocket::class.java)

    private var serverUri: URI
    private var isConnected : Boolean = false
    private var onMessage: (String) -> Unit
    private var canBeOpened: Boolean = true

    private lateinit var ws: SciTraderWebsocketClient

    constructor(serverUri : URI, onMessage : (String) -> Unit)  {
        this.serverUri = serverUri
        this.onMessage = onMessage;
    }

    override fun connect(){
        Log.info("AutoReconnectWebsocket.connect()")

        if (isConnected){
            throw MarketDataServerException("Socket is already connected")
        }

        if (!canBeOpened){
            throw MarketDataServerException("Socket has been closed programmatically, cannot be opened again")
        }

        this.ws = SciTraderWebsocketClient(serverUri,
                { o -> Log.info("Opened websocket to $serverUri") },
                onMessage,
                { ws, i, s, b -> onClosed(ws, i,s,b) },
                { ex -> Log.error("Socket error to $serverUri!", ex) })
        this.ws.connect()
    }

    override fun stop(){
        this.canBeOpened = false
        this.ws.close()
    }

    private fun onClosed(webSocket : SciTraderWebsocketClient, i: Int, s: String, b: Boolean) {

        if (canBeOpened){
            Log.info("Websocket to $serverUri closed. Attempting reconnect...")
            this.isConnected = false
            this.connect()
        } else {
            Log.info("Socket has been closed programmatically, cannot be opened again")
        }
    }
}