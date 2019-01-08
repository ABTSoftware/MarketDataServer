package com.scitrader.marketdataserver.transport

import java.net.URI

interface IAutoReconnectWebsocketFactory{
    fun new(uri : URI, onMessage : (String) -> Unit) : IAutoReconnectWebsocket
}

