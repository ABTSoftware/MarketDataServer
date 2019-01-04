package com.scitrader.marketdataserver.exchange.bitmex;

public class Tick {
  private String timestamp;
  private String symbol;
  private String side;
  private float size;
  private float price;
  private String tickDirection;
  private String trdMatchID;
  private float grossValue;
  private float homeNotional;
  private float foreignNotional;


  // Getter Methods

  public String getTimestamp() {
    return timestamp;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getSide() {
    return side;
  }

  public float getSize() {
    return size;
  }

  public float getPrice() {
    return price;
  }

  public String getTickDirection() {
    return tickDirection;
  }

  public String getTrdMatchID() {
    return trdMatchID;
  }

  public float getGrossValue() {
    return grossValue;
  }

  public float getHomeNotional() {
    return homeNotional;
  }

  public float getForeignNotional() {
    return foreignNotional;
  }

  // Setter Methods

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setSide(String side) {
    this.side = side;
  }

  public void setSize(float size) {
    this.size = size;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public void setTickDirection(String tickDirection) {
    this.tickDirection = tickDirection;
  }

  public void setTrdMatchID(String trdMatchID) {
    this.trdMatchID = trdMatchID;
  }

  public void setGrossValue(float grossValue) {
    this.grossValue = grossValue;
  }

  public void setHomeNotional(float homeNotional) {
    this.homeNotional = homeNotional;
  }

  public void setForeignNotional(float foreignNotional) {
    this.foreignNotional = foreignNotional;
  }

  @Override
  public String toString() {
    return String.format("%s, %s, %s at %s on %s", getSymbol(), getSide(), getSize(), getPrice(), getTimestamp());
  }
}
