package com.scitrader.marketdataserver.datastore;

import com.google.inject.Inject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.scitrader.marketdataserver.common.MarketDataServerException;
import com.scitrader.marketdataserver.common.PriceBar;
import com.scitrader.marketdataserver.common.PriceBarType;
import com.scitrader.marketdataserver.exchange.bitmex.Tick;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.joda.time.DateTime;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.scitrader.marketdataserver.common.PriceBarType.*;

public class TickAggregator implements ITickAggregator {

  private IMongoDbService mongoDbService;

  private static final Logger Log = LogManager.getLogger(TickAggregator.class);

  @Inject
  public TickAggregator(IMongoDbService mongoDbService){
    this.mongoDbService = mongoDbService;
  }

  /*
          public async Task<IList<PriceBarDto>> SelectHistoricalPrices(InstrumentDto instrument, DateTimeOffset startDate, DateTimeOffset endDate, PriceBarPeriod period)
        {
            Validate.NotNull(instrument, "instrument");
            var coll = getCollection(instrument, period);
            var timePeriod = period.ToTimeSpan();
            startDate = DateUtil.RoundDown(startDate, timePeriod);
            endDate = DateUtil.RoundDown(endDate, timePeriod);
            var builder = Builders<PriceBarDto>.Filter;
            var filter =  builder.Gte(p => p.Id, startDate.UtcDateTime)
                        & builder.Lte(p => p.Id, endDate.UtcDateTime);
            var prices = await coll.Find(filter).SortBy(p=>p.Id).ToListAsync();
            return prices;
        }
   */

  public List<PriceBar> getPriceBars(String instrument, DateTime from, DateTime to, PriceBarType barType){

    Log.info(String.format("Received request for instrument %s from %s to $s, PriceBarType $s", instrument, from, to, barType));

    if (!this.mongoDbService.containsCollection(instrument)){
      throw new MarketDataServerException("The collection name " + instrument + " does not exist in the Database");
    }

    String symbol = instrument.substring(instrument.indexOf(':')+1, instrument.length() - 1);
    Log.info(String.format("Parsing symbol %s from '%s'", symbol, instrument));

    long startDate = from.getMillis();
    long endDate = to.getMillis();
    MongoCollection<Document> collection = this.mongoDbService.getTickDatabase().getCollection(instrument);

    Bson filter = Filters.and(Filters.gte("Time", startDate), Filters.lte("Time", endDate));

    FindIterable<Document> foundTicks = collection.find(filter);

    return this.aggregateIntoPriceBars(foundTicks, from, to, barType);
  }

  private List<PriceBar> aggregateIntoPriceBars(FindIterable<Document> foundTicks, DateTime from, DateTime to, PriceBarType barType) {
    switch(barType){
      case TickBar: return aggregateIntoTickBars(foundTicks, from, to);
      case VolumeBar: return aggregateIntoVolumeBars(foundTicks, from, to);
      case TimeBar: return aggregateIntoTimeBars(foundTicks, from, to);
      default:
        throw new MarketDataServerException("Unknown PriceBarType: " + barType);
    }
  }

  private List<PriceBar> aggregateIntoTimeBars(FindIterable<Document> foundTicks, DateTime from, DateTime to) {
    throw new MarketDataServerException("TODO");
  }

  private List<PriceBar> aggregateIntoVolumeBars(FindIterable<Document> foundTicks, DateTime from, DateTime to) {
    throw new MarketDataServerException("TODO");
  }

  private List<PriceBar> aggregateIntoTickBars(FindIterable<Document> foundTicks, DateTime from, DateTime to) {
    throw new MarketDataServerException("TODO");
  }
}

