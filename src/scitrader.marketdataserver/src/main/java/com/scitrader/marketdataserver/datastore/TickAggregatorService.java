package com.scitrader.marketdataserver.datastore;

import com.google.inject.Inject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.scitrader.marketdataserver.common.MarketDataServerException;
import com.scitrader.marketdataserver.common.Model.PriceBar;
import com.scitrader.marketdataserver.common.Model.PriceBarType;
import com.scitrader.marketdataserver.datastore.aggregators.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.joda.time.DateTime;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TickAggregatorService implements ITickAggregatorService {

  private IMongoDbService mongoDbService;

  private static final Logger Log = LogManager.getLogger(TickAggregatorService.class);

  @Inject
  public TickAggregatorService(IMongoDbService mongoDbService){
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

  @Override
  public List<PriceBar> getPriceBars(
          String exchangeCode,
          String marketDataIdentifier,
          DateTime from,
          DateTime to,
          PriceBarType barType,
          Object barTypeArg){

    String fullInstrumentName = exchangeCode + ":" + marketDataIdentifier;

    if (!this.mongoDbService.containsCollection(fullInstrumentName)){
      throw new MarketDataServerException("The collection name '" + fullInstrumentName + "' does not exist in the Database");
    }

    Log.info("Fetching DB collection " + fullInstrumentName);
    MongoCollection<Document> collection = this.mongoDbService.getTickDatabase().getCollection(fullInstrumentName);

    long startDate = from.getMillis();
    long endDate = to.getMillis();

    Log.info(String.format("Filtering by start='%d', end='%d", startDate, endDate));
    Bson filter = Filters.and(Filters.gte("Time", startDate), Filters.lte("Time", endDate));
    FindIterable<Document> foundTicks = collection.find(filter);

    return this.getAggregatorFor(barType)
               .aggregateIntoPriceBars(foundTicks, from, to, barType, barTypeArg);
  }

  private ITickAggregator getAggregatorFor(PriceBarType barType){
    switch(barType){
      case Tick: return new TickTickAggregator();
      case Renko: return new RenkoTickAggregator();
      case Volume: return new VolumeTickAggregator();
      case Time: return new TimeTickAggregator();
      case Range: return new RangeTickAggregator();
      default: throw new MarketDataServerException("The PriceBarType " + barType + " has no known aggregator");
    }
  }
}

