package com.scitrader.marketdataserver.datastore.aggregators;

import com.mongodb.client.FindIterable;
import com.scitrader.marketdataserver.common.MarketDataServerException;
import com.scitrader.marketdataserver.common.Model.PriceBar;
import com.scitrader.marketdataserver.common.Model.PriceBarType;
import org.bson.Document;
import org.joda.time.DateTime;

import java.util.List;

public class TickTickAggregator implements ITickAggregator {

  @Override
  public List<PriceBar> aggregateIntoPriceBars(FindIterable<Document> foundTicks, DateTime from, DateTime to, PriceBarType barType, Object arg) {
    throw new MarketDataServerException("Not implemented");
  }
}
