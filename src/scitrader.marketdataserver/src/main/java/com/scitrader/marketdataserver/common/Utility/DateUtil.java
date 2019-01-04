package com.scitrader.marketdataserver.common.Utility;

import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

public class DateUtil {

  private static final DateTimeFormatter tickFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ").withLocale(Locale.US)
          .withChronology(ISOChronology.getInstanceUTC());

  public static DateTimeFormatter getTickFormatter(){
    return tickFormatter;
  }
}
