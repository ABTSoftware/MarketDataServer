package com.scitrader.marketdataserver.transport

import com.google.inject.Inject
import java.net.URI

class AutoReconnectWebsocketFactory : IAutoReconnectWebsocketFactory {

    @Inject
    constructor(){
    }

    override fun new(uri : URI, onMessage: (String) -> Unit): IAutoReconnectWebsocket {
        return AutoReconnectWebsocket(uri, { m -> onMessage(m)} )
    }
}