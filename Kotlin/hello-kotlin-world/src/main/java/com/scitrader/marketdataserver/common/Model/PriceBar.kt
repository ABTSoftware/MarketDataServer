package com.scitrader.marketdataserver.common.Model

import com.jsoniter.annotation.JsonCreator
import com.jsoniter.annotation.JsonProperty
import org.joda.time.DateTime
import org.json.JSONObject

data class PriceBar (var time: DateTime,
                     var open : Double,
                     var high : Double,
                     var low : Double,
                     var close : Double,
                     var volume : Double) {

    fun toJson(): String {
        val jsonString = JSONObject()
                .put("time", time.millis)
                .put("open", open)
                .put("high", high)
                .put("low", low)
                .put("close", close)
                .put("volume", volume)
                .toString()
        return jsonString
    }
}