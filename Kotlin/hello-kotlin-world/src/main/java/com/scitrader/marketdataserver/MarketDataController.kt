package com.scitrader.marketdataserver

import org.apache.logging.log4j.LogManager
import spark.Request
import spark.Response
import spark.Spark.*

class MarketDataController : IMarketDataController {

    internal var Log = LogManager.getLogger(MarketDataController::class.java)

    override fun Init() {

        // Setup Rest API endpoints
        get("/hello") { r, rs -> onHello(r, rs) }

    }

    private fun onHello(request: Request, response: Response): Any {
        Log.info("Received hello request")
        return "Affirmative Dave, the Coat-lin com.scitrader.marketdataserver is online"
    }
}