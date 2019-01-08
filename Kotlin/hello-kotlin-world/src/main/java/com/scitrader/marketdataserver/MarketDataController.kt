package com.scitrader.marketdataserver

import com.google.inject.Inject
import com.jsoniter.output.JsonStream
import com.scitrader.marketdataserver.common.Model.PriceBarType
import com.scitrader.marketdataserver.common.utility.DateUtil
import com.scitrader.marketdataserver.datastore.ITickAggregatorService
import org.apache.logging.log4j.LogManager
import spark.Request
import spark.Response
import spark.Spark.exception
import spark.Spark.get

class MarketDataController @Inject constructor(val tickAggregator : ITickAggregatorService) : IMarketDataController {

    internal var Log = LogManager.getLogger(MarketDataController::class.java)
    internal val StatusOk : Int = 200

    override fun Init() {

        // Setup Rest API endpoints
        get("/hello") { r, rs -> onHello(r, rs) }

        get("marketdata/:exchange/:symbol/:start/:end/:bartype/:arg") { r, rs -> onMarketData(r, rs) }

        exception(RuntimeException::class.java) { e, req, res -> onError(e, req, res) }
    }

    private fun onError(e: RuntimeException, req: Request, res: Response) {
        Log.error("Error in request " + req.url(), e)
        res.status(400)
        res.body(e.toString())
    }

    private fun onMarketData(request: Request, response: Response): Any {
        val exchangeCode = request.params("exchange")
        val marketidentifiercode = request.params("symbol")
        val startDate = request.params("start")
        val endDate = request.params("end")
        val barType = request.params("bartype")
        val arg = request.params("arg")

        Log.info(String.format("Received marketdata request with exchange=%s, symbol=%s, start=%s, end=%s, bartype=%s, arg=%s",
                exchangeCode,
                marketidentifiercode,
                startDate,
                endDate,
                barType,
                arg))

        val priceBarType = PriceBarType.valueOf(barType)
        val from = DateUtil.tickFormatter.parseDateTime(startDate)
        val to = DateUtil.tickFormatter.parseDateTime(endDate)

        val priceBars = tickAggregator.getPriceBars(
                exchangeCode,
                marketidentifiercode,
                from,
                to,
                priceBarType,
                arg)

        response.status(StatusOk)
        return JsonStream.serialize(priceBars)
    }

    private fun onHello(request: Request, response: Response): Any {
        Log.info("Received hello request from " + request.ip())
        response.status(StatusOk)
        return "Affirmative Dave, the Coat-lin com.scitrader.marketdataserver is online"
    }
}