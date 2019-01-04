package com.scitrader.marketdataserver.common.Utility;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.util.Locale;

public class DateUtil {

  private static final DateTimeFormatter tickFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ").withLocale(Locale.US)
          .withChronology(ISOChronology.getInstanceUTC());

  public static DateTimeFormatter getTickFormatter(){
    return tickFormatter;
  }

  public static DateTime RoundDown(DateTime value, Period nearest)
  {
    return new DateTime(RoundDown(value.getMillis(), nearest.getMillis()));
  }

  public static DateTime RoundUp(DateTime value, Period nearest)
  {
    return new DateTime(RoundUp(value.getMillis(), nearest.getMillis()));
  }

  private static long RoundDown(long value, long nearest)
  {
    return (long)(Math.floor(value / nearest) * nearest);
  }

  private static long RoundUp(long value, long nearest)
  {
    return (long)(Math.ceil(value / nearest) * nearest);
  }
}
