package com.scitrader.marketdataserver.exchange.bitmex.messages

import java.util.ArrayList

class TicksMessage {
    var table: String? = null
    var action: String? = null
    internal var data = ArrayList<Tick>()
}