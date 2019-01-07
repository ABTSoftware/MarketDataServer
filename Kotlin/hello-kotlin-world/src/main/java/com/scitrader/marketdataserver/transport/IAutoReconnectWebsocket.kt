package com.scitrader.marketdataserver.transport

interface IAutoReconnectWebsocket{
    fun connect()
    fun stop()
}