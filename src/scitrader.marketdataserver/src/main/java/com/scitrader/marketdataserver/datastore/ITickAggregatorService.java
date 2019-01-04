package com.scitrader.marketdataserver.datastore;

import com.scitrader.marketdataserver.common.Model.PriceBar;
import com.scitrader.marketdataserver.common.Model.PriceBarType;
import org.joda.time.DateTime;

import java.util.List;

public interface ITickAggregatorService {

  List<PriceBar> getPriceBars(String exchangeCode, String marketDataIdentifier, DateTime from, DateTime to, PriceBarType barType);
}
