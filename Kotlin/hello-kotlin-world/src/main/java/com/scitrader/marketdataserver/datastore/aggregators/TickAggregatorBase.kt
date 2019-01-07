package com.scitrader.marketdataserver.datastore.aggregators

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.apache.logging.log4j.LogManager
import org.bson.Document
import org.joda.time.DateTime

abstract class TickAggregatorBase : ITickAggregator {

    protected val Log = LogManager.getLogger(this.javaClass)

    internal fun fetchTicks(ticksCollection: MongoCollection<Document>, from: DateTime, to: DateTime): FindIterable<Document> {
        val startDate = from.millis
        val endDate = to.millis

        Log.info(String.format("Filtering by start='%d', end='%d", startDate, endDate))
        val filter = Filters.and(Filters.gte("Time", startDate), Filters.lte("Time", endDate))
        return ticksCollection.find(filter)
    }
}

