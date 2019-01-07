package com.scitrader.marketdataserver.datastore.aggregators;

import com.mongodb.client.MongoCollection;
import com.scitrader.marketdataserver.common.Model.PriceBar;
import com.scitrader.marketdataserver.common.Model.PriceBarType;
import org.bson.Document;
import org.joda.time.DateTime;

import java.util.List;

public interface ITickAggregator {

  List<PriceBar> fetchAndAggregate(MongoCollection<Document> ticksCollection, String symbol, DateTime from, DateTime to, PriceBarType priceBarType, Object arg);
}

