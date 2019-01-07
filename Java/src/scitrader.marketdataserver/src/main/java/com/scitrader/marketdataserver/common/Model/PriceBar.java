package com.scitrader.marketdataserver.common.Model;

import com.jsoniter.annotation.JsonProperty;
import org.joda.time.DateTime;

public class PriceBar {

  @JsonProperty("time")
  private DateTime time;
  @JsonProperty("open")
  private double open;
  @JsonProperty("high")
  private double high;
  @JsonProperty("low")
  private double low;
  @JsonProperty("close")
  private double close;
  @JsonProperty("volume")
  private double volume;

  public PriceBar(){
  }
  
  public PriceBar(DateTime time, double open, double high, double low, double close, double volume){
    this.time = time;
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.volume = volume;
  }

  public DateTime getTime() {
    return time;
  }

  public void setTime(DateTime time) {
    this.time = time;
  }

  public double getOpen() {
    return open;
  }

  public void setOpen(double open) {
    this.open = open;
  }

  public double getHigh() {
    return high;
  }

  public void setHigh(double high) {
    this.high = high;
  }

  public double getLow() {
    return low;
  }

  public void setLow(double low) {
    this.low = low;
  }

  public double getClose() {
    return close;
  }

  public void setClose(double close) {
    this.close = close;
  }

  public double getVolume() {
    return volume;
  }

  public void setVolume(double volume) {
    this.volume = volume;
  }
}
