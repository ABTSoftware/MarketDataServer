package com.scitrader.marketdataserver.exchange.bitmex;

import com.scitrader.marketdataserver.common.Utility.DateUtil;
import org.bson.Document;
import org.joda.time.DateTime;

import java.util.Locale;

public class Tick  {
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

  public DateTime getTimeStampAsDate(){
    // e.g. 2019-01-04T12:17:00.980Z
    DateTime dt = DateUtil.getTickFormatter().parseDateTime(getTimestamp());
    return dt;
  }

  public Document toBsonDocument(){
    Document doc = new Document();

    doc.append("_id", getTrdMatchID());
    doc.append("Time", getTimeStampAsDate().getMillis());
    doc.append("Price", getPrice());
    doc.append("Size", getSize() * (getSide().toUpperCase(Locale.US) == "BUY" ? 1f : -1f));
    return doc;
  }

  public static Tick fromBsonDocument(String symbol, Document document){
    Tick t = new Tick();
    t.setSymbol(symbol);
    t.setSize((float)document.get("Size"));
    t.setPrice((float)document.get("Price"));
    t.setTimestamp(new DateTime((long)document.get("Time")).toString(DateUtil.getTickFormatter()));
    t.setSide(t.getSize() < 0 ? "Sell" : "Buy");
    return t;
  }
}
