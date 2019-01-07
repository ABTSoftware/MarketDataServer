package com.scitrader.marketdataserver.datastore.aggregators

import com.mongodb.client.MongoCollection
import com.scitrader.marketdataserver.common.MarketDataServerException
import com.scitrader.marketdataserver.common.Model.PriceBar
import com.scitrader.marketdataserver.common.Model.PriceBarType
import org.bson.Document
import org.joda.time.DateTime

class RenkoTickAggregator : TickAggregatorBase(), ITickAggregator {

    override fun fetchAndAggregate(ticksCollection: MongoCollection<Document>, symbol: String, from: DateTime, to: DateTime, barType: PriceBarType, arg: Any): List<PriceBar> {
        throw MarketDataServerException("Not implemented")
    }
}