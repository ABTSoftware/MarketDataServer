package com.scitrader.marketdataserver.datastore.aggregators;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.scitrader.marketdataserver.common.MarketDataServerException;
import com.scitrader.marketdataserver.common.Model.PriceBar;
import com.scitrader.marketdataserver.common.Model.PriceBarType;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.joda.time.DateTime;

import java.util.List;

public class RangeTickAggregator extends TickAggregatorBase implements ITickAggregator {

  @Override
  public List<PriceBar> fetchAndAggregate(MongoCollection<Document> ticksCollection, String symbol, DateTime from, DateTime to, PriceBarType barType, Object arg) {
    throw new MarketDataServerException("Not implemented");
  }
}
