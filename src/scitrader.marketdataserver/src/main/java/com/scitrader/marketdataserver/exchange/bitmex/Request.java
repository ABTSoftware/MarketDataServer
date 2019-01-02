package com.scitrader.marketdataserver.exchange.bitmex;

public class Request {
  private String op;
  private String args;


  // Getter Methods

  public String getOp() {
    return op;
  }

  public String getArgs() {
    return args;
  }

  // Setter Methods

  public void setOp(String op) {
    this.op = op;
  }

  public void setArgs(String args) {
    this.args = args;
  }
}
