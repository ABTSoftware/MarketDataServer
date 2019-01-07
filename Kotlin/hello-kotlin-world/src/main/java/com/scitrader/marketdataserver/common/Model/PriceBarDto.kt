package com.scitrader.marketdataserver.common.Model

import com.jsoniter.annotation.JsonProperty
import org.joda.time.DateTime

// Data class implements get/set and .equals(), getHashCode, toString()
data class PriceBar (@JsonProperty("time") var time: DateTime,
                     @JsonProperty("open") var open : Double,
                     @JsonProperty("high") var high : Double,
                     @JsonProperty("low") var low : Double,
                     @JsonProperty("close") var close : Double,
                     @JsonProperty("volume") var volume : Double) {
}