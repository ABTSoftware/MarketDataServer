package com.scitrader.marketdataserver.transport

import com.scitrader.marketdataserver.common.MarketDataServerException
import org.apache.logging.log4j.LogManager
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class SciTraderWebsocketClient(serverUri: URI,
                               private val onOpenFunc: (ServerHandshake) -> Unit,
                               private val onMessageFunc: (String) -> Unit,
                               private val onCloseFunc: (SciTraderWebsocketClient, Int, String, Boolean) -> Unit,
                               private val onErrorFunc: (Exception) -> Unit) : WebSocketClient(serverUri) {

    companion object {
        private val Log = LogManager.getLogger(SciTraderWebsocketClient::class.java)
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