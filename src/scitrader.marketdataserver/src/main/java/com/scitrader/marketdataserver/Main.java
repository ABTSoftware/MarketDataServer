package com.scitrader.marketdataserver;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

  private final static Logger log = LogManager.getLogger(Main.class);

  public static void main(String[] args) {
    //Injector injector = Guice.createInjector(new BasicModule());
    log.info("I am in your base, killing your doods");
  }
}
