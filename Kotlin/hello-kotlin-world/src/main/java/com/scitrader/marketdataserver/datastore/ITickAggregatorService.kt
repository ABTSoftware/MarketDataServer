package com.scitrader.marketdataserver.datastore

import com.scitrader.marketdataserver.common.Model.PriceBar
import com.scitrader.marketdataserver.common.Model.PriceBarType
import org.joda.time.DateTime

interface ITickAggregatorService {

    fun getPriceBars(
            exchangeCode: String,
            marketDataIdentifier: String,
            from: DateTime,
            to: DateTime,
            barType: PriceBarType,
            barTypeArg: Any): List<PriceBar>
}

