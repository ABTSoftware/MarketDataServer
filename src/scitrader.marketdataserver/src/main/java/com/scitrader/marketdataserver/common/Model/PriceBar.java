package com.scitrader.marketdataserver.common.Model;

import com.jsoniter.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.math.BigDecimal;

public class PriceBar {

  @JsonProperty("time")
  private DateTime time;
  @JsonProperty("open")
  private BigDecimal open;
  @JsonProperty("high")
  private BigDecimal high;
  @JsonProperty("low")
  private BigDecimal low;
  @JsonProperty("close")
  private BigDecimal close;
  @JsonProperty("volume")
  private BigDecimal volume;


  public DateTime getTime() {
    return time;
  }

  public void setTime(DateTime time) {
    this.time = time;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public void setOpen(BigDecimal open) {
    this.open = open;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public void setHigh(BigDecimal high) {
    this.high = high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public void setLow(BigDecimal low) {
    this.low = low;
  }

  public BigDecimal getClose() {
    return close;
  }

  public void setClose(BigDecimal close) {
    this.close = close;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }
}
