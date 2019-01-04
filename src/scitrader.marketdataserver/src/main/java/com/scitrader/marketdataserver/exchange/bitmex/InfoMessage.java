package com.scitrader.marketdataserver.exchange.bitmex;

public class InfoMessage {
  private String info;
  private String version;
  private String timestamp;
  private String docs;
  Limit LimitObject;

  // Getter Methods

  public String getInfo() {
    return info;
  }

  public String getVersion() {
    return version;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getDocs() {
    return docs;
  }

  public Limit getLimit() {
    return LimitObject;
  }

  // Setter Methods

  public void setInfo(String info) {
    this.info = info;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public void setDocs(String docs) {
    this.docs = docs;
  }

  public void setLimit(Limit limitObject) {
    this.LimitObject = limitObject;
  }
}
