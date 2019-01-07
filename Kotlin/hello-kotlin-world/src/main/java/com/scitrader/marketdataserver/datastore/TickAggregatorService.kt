package com.scitrader.marketdataserver.datastore

import com.google.inject.Inject
import com.scitrader.marketdataserver.common.MarketDataServerException
import com.scitrader.marketdataserver.common.Model.PriceBar
import com.scitrader.marketdataserver.common.Model.PriceBarType
import com.scitrader.marketdataserver.datastore.aggregators.*
import org.apache.logging.log4j.LogManager
import org.joda.time.DateTime

class TickAggregatorService @Inject
constructor(private val mongoDbService: IMongoDbService) : ITickAggregatorService {

    companion object {

        private val Log = LogManager.getLogger(TickAggregatorService::class.java)
    }

    override fun getPriceBars(
            exchangeCode: String,
            marketDataIdentifier: String,
            from: DateTime,
            to: DateTime,
            barType: PriceBarType,
            barTypeArg: Any): List<PriceBar> {

        val fullInstrumentName = "$exchangeCode:$marketDataIdentifier"

        if (!this.mongoDbService.containsCollection(fullInstrumentName)) {
            throw MarketDataServerException("The collection name '$fullInstrumentName' does not exist in the Database")
        }

        Log.info("Fetching DB collection $fullInstrumentName")
        val collection = this.mongoDbService.tickDatabase.getCollection(fullInstrumentName)

        return this.getAggregatorFor(barType)
                .fetchAndAggregate(collection, marketDataIdentifier, from, to, barType, barTypeArg)
    }

    private fun getAggregatorFor(barType: PriceBarType): ITickAggregator {
        when (barType) {
            PriceBarType.Tick -> return TickTickAggregator()
            PriceBarType.Renko -> return RenkoTickAggregator()
            PriceBarType.Volume -> return VolumeTickAggregator()
            PriceBarType.Time -> return TimeTickAggregator()
            PriceBarType.Range -> return RangeTickAggregator()
            else -> throw MarketDataServerException("The PriceBarType $barType has no known aggregator")
        }
    }
}