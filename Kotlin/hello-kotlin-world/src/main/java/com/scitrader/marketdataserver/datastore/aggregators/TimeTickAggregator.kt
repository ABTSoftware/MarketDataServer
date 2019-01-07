package com.scitrader.marketdataserver.datastore.aggregators

import com.mongodb.client.MongoCollection
import com.scitrader.marketdataserver.common.MarketDataServerException
import com.scitrader.marketdataserver.common.Model.PriceBar
import com.scitrader.marketdataserver.common.Model.PriceBarType
import com.scitrader.marketdataserver.common.utility.DateUtil
import com.scitrader.marketdataserver.common.utility.Guard
import com.scitrader.marketdataserver.exchange.bitmex.messages.Tick
import org.bson.Document
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.Period
import java.util.ArrayList

class TimeTickAggregator : TickAggregatorBase(), ITickAggregator {

    override fun fetchAndAggregate(ticksCollection: MongoCollection<Document>, symbol: String, from: DateTime, to: DateTime, barType: PriceBarType, arg: Any): List<PriceBar> {

        var from = from
        var to = to

        Guard.assertIsTrue(barType === PriceBarType.Time, "PriceBarType must be Time")

        // Get time interval
        val timeInterval: Duration
        try {
            timeInterval = Period.parse(arg as String).toStandardDuration()
        } catch (ex: Exception) {
            throw MarketDataServerException("Cannot parse the object " + arg.toString() + " as an Interval")
        }

        // Round down to nearest time bar
        Log.info(String.format("Round down date %s by %s", from, timeInterval))
        from = DateUtil.RoundDown(from, timeInterval)
        Log.info(String.format("Round down date %s by %s", to, timeInterval))
        to = DateUtil.RoundDown(to, timeInterval)

        Log.info(String.format("Rounding down Startdate=%s, Enddate=%s to the nearest Interval=%s", from, to, timeInterval))

        // Fetch ticks
        val ticks = super.fetchTicks(ticksCollection, from, to)

        // Aggregate to prie bars
        val priceBars = ArrayList<PriceBar>()

        var open = 0.0
        var high = java.lang.Double.MIN_VALUE
        var low = java.lang.Double.MAX_VALUE
        var close = 0.0
        var volume = 0.0
        var tickCount: Long = 0
        var barStartTime = from
        var barEndTime = barStartTime.withDurationAdded(timeInterval, 1)

        val itr = ticks.iterator()
        while (itr.hasNext()) {
            val tick = Tick.fromBsonDocument(symbol, itr.next())
            val price = tick.price

            if (tick.timeStampAsDate!!.isBefore(barEndTime) && itr.hasNext()) {
                close = price.toDouble()
                volume += Math.abs(tick.size)

                if (tickCount == 0L) {
                    high = price.toDouble()
                    low = price.toDouble()
                    open = price.toDouble()
                } else {
                    high = Math.max(high, price.toDouble())
                    low = Math.min(price.toDouble(), low)
                }

                tickCount++
            } else {
                // Bar time exceeded, add price bar and setup for next tick
                priceBars.add(PriceBar(barStartTime, open, high, low, close, volume))

                tickCount = 1
                open = price.toDouble()
                high = price.toDouble()
                low = price.toDouble()
                close = price.toDouble()
                volume = Math.abs(tick.size).toDouble()

                barStartTime = barStartTime.withDurationAdded(timeInterval, 1)
                barEndTime = barEndTime.withDurationAdded(timeInterval, 1)
            }
        }

        return priceBars
    }
}