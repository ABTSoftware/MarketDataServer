package com.scitrader.marketdataserver.exchange.bitmex.messages

//{"status":500,"error":"operation timed out","meta":{"request":{"op":"subscribe","args":["trade:ETHUSD"]}}}
class StatusMessage {
    var status: Float = 0.toFloat()
    var error: String? = null
    var meta: Meta? = null
}