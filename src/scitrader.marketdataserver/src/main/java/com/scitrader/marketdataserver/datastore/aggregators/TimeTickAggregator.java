package com.scitrader.marketdataserver.datastore.aggregators;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Collation;
import com.scitrader.marketdataserver.common.MarketDataServerException;
import com.scitrader.marketdataserver.common.Model.PriceBar;
import com.scitrader.marketdataserver.common.Model.PriceBarType;
import com.scitrader.marketdataserver.common.Utility.DateUtil;
import com.scitrader.marketdataserver.common.Utility.Guard;
import com.scitrader.marketdataserver.exchange.bitmex.Tick;
import org.bson.Document;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TimeTickAggregator extends TickAggregatorBase implements ITickAggregator {

  @Override
  public List<PriceBar> fetchAndAggregate(MongoCollection<Document> ticksCollection, String symbol, DateTime from, DateTime to, PriceBarType barType, Object arg) {

    Guard.assertNotNull(arg, "arg");
    Guard.assertIsTrue(barType == PriceBarType.Time, "PriceBarType must be Time");

    // Get time interval
    Period timeInterval;
    try{
      timeInterval = Period.parse((String)arg);
    }
    catch(Exception ex){
      throw new MarketDataServerException("Cannot parse the object " + arg.toString() + " as an Interval");
    }

    // Round down to nearest time bar
    Log.info(String.format("Round down date %s by %s", from, timeInterval));
    from = DateUtil.RoundDown(from, timeInterval);
    Log.info(String.format("Round down date %s by %s", to, timeInterval));
    to = DateUtil.RoundDown(to, timeInterval);

    Log.info(String.format("Rounding down Startdate=%s, Enddate=%s to the nearest Interval=%s", from, to, timeInterval));

    // Fetch ticks
    FindIterable<Document> ticks = super.fetchTicks(ticksCollection, from, to);

    // Aggregate to prie bars
    List<PriceBar> priceBars = new ArrayList<>();

    double open = 0;
    double high = Double.MIN_VALUE;
    double low = Double.MAX_VALUE;
    double close = 0;
    boolean hasTicks = false;
    DateTime barStartTime = from;

    for(Document d : ticks)
    {
      Tick tick = Tick.fromBsonDocument(symbol, d);
      if (tick.getTimeStampAsDate().isBefore( barStartTime.withPeriodAdded(timeInterval, 0)))
      {
        double price = tick.getPrice();
        close = price;

        if (!hasTicks)
        {
          high = price;
          low = price;
          open = price;
          hasTicks = true;
        }
        else
        {
          high = Math.max(high, price);
          low = Math.min(price, low);
        }
      }
      else
      {
        if (hasTicks)
        {
          priceBars.add(new PriceBar());

          hasTicks = false;
        }
        else
          barStartTime = barStartTime.withPeriodAdded(timeInterval, 0);
      }
    }

    return priceBars;
  }
}
