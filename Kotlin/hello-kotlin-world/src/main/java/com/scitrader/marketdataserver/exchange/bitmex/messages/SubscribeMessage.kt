package com.scitrader.marketdataserver.exchange.bitmex.messages

class SubscribeMessage {
    var success: Boolean = false
    var subscribe: String? = null
    var request: Request? = null
}