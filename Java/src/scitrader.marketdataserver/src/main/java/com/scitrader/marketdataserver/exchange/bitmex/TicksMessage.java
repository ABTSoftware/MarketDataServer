package com.scitrader.marketdataserver.exchange.bitmex;

import java.util.ArrayList;

public class TicksMessage {
  private String table;
  private String action;
  ArrayList< Tick > data = new ArrayList < Tick > ();


  // Getter Methods
  public String getTable() {
    return table;
  }

  public String getAction() {
    return action;
  }

  // Setter Methods

  public void setTable(String table) {
    this.table = table;
  }

  public void setAction(String action) {
    this.action = action;
  }
}

