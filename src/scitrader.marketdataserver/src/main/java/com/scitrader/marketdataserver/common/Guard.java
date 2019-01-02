package com.scitrader.marketdataserver.common;

public class Guard {

  public static void NotNull(Object o, String message){
    if (o == null){
      throw new ArgumentNullException(message);
    }
  }
}

