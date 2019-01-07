package com.scitrader.marketdataserver.exchange.bitmex;

public class SubscribeMessage {
  private boolean success;
  private String subscribe;
  Request RequestObject;


  // Getter Methods

  public boolean getSuccess() {
    return success;
  }

  public String getSubscribe() {
    return subscribe;
  }

  public Request getRequest() {
    return RequestObject;
  }

  // Setter Methods

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public void setSubscribe(String subscribe) {
    this.subscribe = subscribe;
  }

  public void setRequest(Request requestObject) {
    this.RequestObject = requestObject;
  }
}


