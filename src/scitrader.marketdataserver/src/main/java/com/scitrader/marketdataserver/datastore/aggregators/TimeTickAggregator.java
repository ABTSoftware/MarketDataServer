package com.scitrader.marketdataserver.datastore.aggregators;

import com.mongodb.client.FindIterable;
import com.scitrader.marketdataserver.common.MarketDataServerException;
import com.scitrader.marketdataserver.common.Model.PriceBar;
import com.scitrader.marketdataserver.common.Model.PriceBarType;
import com.scitrader.marketdataserver.common.Utility.Guard;
import org.bson.Document;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.List;

public class TimeTickAggregator implements ITickAggregator {

  @Override
  public List<PriceBar> aggregateIntoPriceBars(FindIterable<Document> foundTicks, DateTime from, DateTime to, PriceBarType barType, Object arg) {

    Guard.assertNotNull(arg, "arg");
    Guard.assertIsTrue(barType == PriceBarType.Time, "PriceBarType must be Time");

    Interval timeInterval;
    try{
      Interval interval = Interval.parse((String)arg);
    }
    catch(Exception ex){
      throw new MarketDataServerException("Cannot parse the object " + arg.toString() + " as an Interval");
    }

    throw new MarketDataServerException("Not implemented");
  }
}
