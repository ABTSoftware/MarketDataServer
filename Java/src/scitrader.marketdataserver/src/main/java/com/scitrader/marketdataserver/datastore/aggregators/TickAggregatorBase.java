package com.scitrader.marketdataserver.datastore.aggregators;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.scitrader.marketdataserver.exchange.bitmex.Tick;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.conversions.Bson;
import org.joda.time.DateTime;
import org.bson.Document;

public abstract class TickAggregatorBase {

  protected final Logger Log = LogManager.getLogger(this.getClass());

  FindIterable<Document> fetchTicks(MongoCollection<Document> ticksCollection, DateTime from, DateTime to) {
    long startDate = from.getMillis();
    long endDate = to.getMillis();

    Log.info(String.format("Filtering by start='%d', end='%d", startDate, endDate));
    Bson filter = Filters.and(Filters.gte("Time", startDate), Filters.lte("Time", endDate));
    FindIterable<Document> foundTicks = ticksCollection.find(filter);
    return foundTicks;
  }
}
