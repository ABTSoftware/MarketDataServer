package com.scitrader.marketdataserver.common.Utility;

import com.scitrader.marketdataserver.common.ArgumentException;
import com.scitrader.marketdataserver.common.ArgumentNullException;

public class Guard {

  public static void assertNotNull(Object o, String message){
    if (o == null){
      throw new ArgumentNullException(message);
    }
  }

  public static void assertIsTrue(boolean expression, String message) {
    if (expression){
      throw new ArgumentException(message);
    }
  }
}

