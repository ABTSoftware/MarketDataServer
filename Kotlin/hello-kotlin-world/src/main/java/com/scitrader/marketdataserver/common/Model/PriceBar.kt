package com.scitrader.marketdataserver.common.Model

import com.jsoniter.annotation.JsonCreator
import com.jsoniter.annotation.JsonProperty
import org.joda.time.DateTime

class PriceBar () {

    var time : DateTime = DateTime(0)
    var open : Double = 0.0
    var high : Double = 0.0
    var low : Double = 0.0
    var close : Double = 0.0
    var volume : Double = 0.0

    @JsonCreator
    constructor(@JsonProperty("time") time: DateTime,
                @JsonProperty("open") open : Double,
                @JsonProperty("high") high : Double,
                @JsonProperty("low") low : Double,
                @JsonProperty("close") close : Double,
                @JsonProperty("volume") volume : Double) : this(){
        this.time = time
        this.open = open
        this.high = high
        this.low = low
        this.close = close
        this.volume = volume
    }
}