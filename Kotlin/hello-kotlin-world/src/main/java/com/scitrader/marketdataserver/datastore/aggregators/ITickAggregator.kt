package com.scitrader.marketdataserver.datastore.aggregators

import com.mongodb.client.MongoCollection
import com.scitrader.marketdataserver.common.Model.PriceBar
import com.scitrader.marketdataserver.common.Model.PriceBarType
import org.bson.Document
import org.joda.time.DateTime

interface ITickAggregator {

    fun fetchAndAggregate(ticksCollection: MongoCollection<Document>,
                          symbol: String,
                          from: DateTime,
                          to: DateTime,
                          barType: PriceBarType,
                          arg: Any): List<PriceBar>
}

