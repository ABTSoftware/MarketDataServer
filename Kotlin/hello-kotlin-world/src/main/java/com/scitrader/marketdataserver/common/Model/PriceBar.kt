package com.scitrader.marketdataserver.common.Model

import com.jsoniter.annotation.JsonProperty
import org.joda.time.DateTime

class PriceBar () {

    @field:JsonProperty("time")
    var time : DateTime = DateTime(0)
    @field:JsonProperty("open")
    var open : Double = 0.0
    @field:JsonProperty("high")
    var high : Double = 0.0
    @field:JsonProperty("low")
    var low : Double = 0.0
    @field:JsonProperty("close")
    var close : Double = 0.0
    @field:JsonProperty("volume")
    var volume : Double = 0.0

    constructor(time: DateTime,
                open : Double,
                high : Double,
                low : Double,
                close : Double,
                volume : Double) : this(){
        this.time = time
        this.open = open
        this.high = high
        this.low = low
        this.close = close
        this.volume = volume
    }
}