package com.scitrader.marketdataserver;

import com.google.inject.Inject;
import com.jsoniter.output.JsonStream;
import com.scitrader.marketdataserver.common.Utility.DateUtil;
import com.scitrader.marketdataserver.common.Utility.JodaTimeSerializer;
import com.scitrader.marketdataserver.common.Model.PriceBar;
import com.scitrader.marketdataserver.common.Model.PriceBarType;
import com.scitrader.marketdataserver.datastore.ITickAggregatorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;

import java.util.List;

import static spark.Spark.*;

public class MarketDataController implements IMarketDataController {

  private ITickAggregatorService tickAggregator;

  Logger Log = LogManager.getLogger(MarketDataController.class);

  @Inject
  public MarketDataController(ITickAggregatorService tickAggregator){

    this.tickAggregator = tickAggregator;
  }

  @Override
  public void init() {

    JodaTimeSerializer.enable();

    // Setup Rest API endpoints
    get("/hello", (r,rs) -> onHello(r,rs));

    get("marketdata/:exchange/:symbol/:start/:end/:bartype/:arg", (r, rs) -> onMarketData(r,rs));

    exception(RuntimeException.class, (e, req, res) -> onError(e,req,res));
  }

  private void onError(RuntimeException e, Request req, Response res) {
    Log.error("Error in request " + req.url(), e);
    res.status(400);
    res.body(e.toString());
  }

  private Object onMarketData(Request req, Response res) {
    String exchangeCode = req.params("exchange");
    String marketidentifiercode = req.params("symbol");
    String startDate = req.params("start");
    String endDate = req.params("end");
    String barType = req.params("bartype");
    String arg = req.params("arg");

    Log.info(String.format("Received marketdata request with exchange=%s, symbol=%s, start=%s, end=%s, bartype=%s, arg=%s",
            exchangeCode,
            marketidentifiercode,
            startDate,
            endDate,
            barType,
            arg));

    PriceBarType priceBarType = PriceBarType.valueOf(barType);
    DateTime from = DateUtil.getTickFormatter().parseDateTime(startDate);
    DateTime to = DateUtil.getTickFormatter().parseDateTime(endDate);

    List<PriceBar> priceBars = tickAggregator.getPriceBars(
            exchangeCode,
            marketidentifiercode,
            from,
            to,
            priceBarType,
            arg);

    return JsonStream.serialize(priceBars);
  }

  private Object onHello(Request request, Response response) {
    Log.info("Received hello request");
    return "Affirmative Dave, the com.scitrader.marketdataserver is online";
  }
}
