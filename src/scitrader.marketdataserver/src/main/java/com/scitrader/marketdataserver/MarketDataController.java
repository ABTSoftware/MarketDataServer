package com.scitrader.marketdataserver;

import com.google.inject.Inject;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.JsoniterSpi;
import com.scitrader.marketdataserver.common.JodaTimeSerializer;
import com.scitrader.marketdataserver.common.MarketDataServerException;
import com.scitrader.marketdataserver.common.PriceBar;
import com.scitrader.marketdataserver.common.PriceBarType;
import com.scitrader.marketdataserver.datastore.ITickAggregator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;

import java.util.List;

import static spark.Spark.*;

public class MarketDataController implements IMarketDataController {

  private ITickAggregator tickAggregator;

  Logger Log = LogManager.getLogger(MarketDataController.class);

  @Inject
  public MarketDataController(ITickAggregator tickAggregator){

    this.tickAggregator = tickAggregator;
  }

  @Override
  public void init() {

    JodaTimeSerializer.enable();

    // Setup Rest API endpoints
    get("/hello", (r,rs) -> onHello(r,rs));

    get("marketdata/:exchangecode/:marketidentifiercode", (r, rs) -> onMarketData(r,rs));

    exception(RuntimeException.class, (e, req, res) -> onError(e,req,res));
  }

  private void onError(RuntimeException e, Request req, Response res) {
    res.status(400);
    res.body(e.toString());
  }

  private Object onMarketData(Request req, Response res) {
    String exchangeCode = req.params("exchangecode");
    String marketidentifiercode = req.params("marketidentifiercode");

    Log.info("Received marketdata request with exchangecode=%s, marketidentifiercode=%s",
            exchangeCode,
            marketidentifiercode);

    List<PriceBar> priceBars = tickAggregator.getPriceBars(exchangeCode + ":" + marketidentifiercode,
            DateTime.now().minusHours(1), DateTime.now(), PriceBarType.TimeBar);

    return JsonStream.serialize(priceBars);
  }

  private Object onHello(Request request, Response response) {
    Log.info("Received hello request");
    return "Affirmative Dave, the com.scitrader.marketdataserver is online";
  }
}
