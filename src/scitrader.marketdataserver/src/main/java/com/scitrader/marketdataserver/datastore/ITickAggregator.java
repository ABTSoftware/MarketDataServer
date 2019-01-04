package com.scitrader.marketdataserver.datastore;

import com.scitrader.marketdataserver.common.PriceBar;
import com.scitrader.marketdataserver.common.PriceBarType;
import org.joda.time.DateTime;

import java.util.List;

public interface ITickAggregator {

  List<PriceBar> getPriceBars(String instrument, DateTime from, DateTime to, PriceBarType barType);
}
