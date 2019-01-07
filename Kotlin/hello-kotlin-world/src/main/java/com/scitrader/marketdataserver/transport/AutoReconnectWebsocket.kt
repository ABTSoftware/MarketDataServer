package com.scitrader.marketdataserver.transport

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

public class AutoReconnectWebsocket : WebSocketClient {

    constructor(serverUri : URI) : super(serverUri) {
    }

    override fun onOpen(handshakedata: ServerHandshake) {
        //this.onOpen(handshakedata)
    }

    override fun onMessage(message: String) {
        //this.onMessage(message)
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        //this.onClose(code, reason, remote)
    }

    override fun onError(ex: Exception) {
        //this.onError(ex)
    }
}