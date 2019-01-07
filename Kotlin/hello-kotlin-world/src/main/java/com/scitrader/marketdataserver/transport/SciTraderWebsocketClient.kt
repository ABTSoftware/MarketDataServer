package com.scitrader.marketdataserver.transport

import com.scitrader.marketdataserver.common.MarketDataServerException
import com.scitrader.marketdataserver.common.Utility.Guard
import org.apache.logging.log4j.LogManager
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.util.function.Consumer

class SciTraderWebsocketClient(serverUri: URI,
                               private val onOpenFunc: (ServerHandshake) -> Unit,
                               private val onMessageFunc: (String) -> Unit,
                               private val onCloseFunc: (SciTraderWebsocketClient, Int, String, Boolean) -> Unit,
                               private val onErrorFunc: (Exception) -> Unit) : WebSocketClient(serverUri) {

    companion object {
        private val Log = LogManager.getLogger(SciTraderWebsocketClient::class.java)
    }

    init {
        Guard.assertNotNull(serverUri, "Server URI cannot be null")
        Guard.assertNotNull(onOpenFunc, "OnOpen consumer cannot be null")
        Guard.assertNotNull(onMessageFunc, "OnMessage consumer cannot be null")
        Guard.assertNotNull(onCloseFunc, "OnClose consumer cannot be null")
        Guard.assertNotNull(onErrorFunc, "OnError consumer cannot be null")
    }

    override fun connect() {
        try {
            super.connect()
        } catch (ex: Exception) {
            throw MarketDataServerException()
        }
    }

    override fun onOpen(handshakedata: ServerHandshake) {
        this.onOpenFunc(handshakedata)
    }

    override fun onMessage(message: String) {
        this.onMessageFunc(message)
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        this.onCloseFunc(this, code, reason, remote)
    }

    override fun onError(ex: Exception) {
        this.onErrorFunc(ex)
    }
}