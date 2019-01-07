package com.scitrader.marketdataserver.exchange.bitmex.messages

import com.jsoniter.annotation.JsonIgnore
import com.scitrader.marketdataserver.common.Utility.DateUtil
import org.bson.Document
import org.joda.time.DateTime
import java.util.*

class Tick {
    @JsonIgnore
    internal var dateTime: DateTime? = null

    var timestamp: String? = null
    var symbol: String? = null
    var side: String? = null
    var size: Float = 0.toFloat()
    var price: Float = 0.toFloat()
    var tickDirection: String? = null
    var trdMatchID: String? = null
    var grossValue: Float = 0.toFloat()
    var homeNotional: Float = 0.toFloat()
    var foreignNotional: Float = 0.toFloat()

    // e.g. 2019-01-04T12:17:00.980Z
    var timeStampAsDate: DateTime?
        get() {

            if (dateTime == null && timestamp != null) {
                dateTime = DateUtil.tickFormatter.parseDateTime(timestamp)
            }
            return dateTime
        }
        set(dateTime) {
            this.dateTime = dateTime
        }

    override fun toString(): String {
        return String.format("%s, %s, %s at %s on %s", symbol, side, size, price, timestamp)
    }

    fun toBsonDocument(): Document {
        val doc = Document()

        doc.append("_id", trdMatchID)
        doc.append("Time", timeStampAsDate!!.millis)
        doc.append("Price", price)
        doc.append("Size", size * if (side!!.toUpperCase(Locale.US) === "BUY") 1f else -1f)

        return doc
    }

    companion object {

        fun fromBsonDocument(symbol: String, document: Document): Tick {
            val t = Tick()
            t.symbol = symbol
            t.size = (document["Size"] as Double).toFloat()
            t.price = (document["Price"] as Double).toFloat()
            t.timeStampAsDate = DateTime(document["Time"] as Long)
            t.side = if (t.size < 0) "Sell" else "Buy"
            return t
        }
    }
}