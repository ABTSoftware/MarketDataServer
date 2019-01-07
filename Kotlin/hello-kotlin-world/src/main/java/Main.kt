package com.scitrader.marketdataserver

import com.google.inject.Guice
import com.google.inject.Injector
import org.apache.logging.log4j.LogManager

object MainLogger {
    val log = LogManager.getLogger(MainLogger::class.java)
}

fun main(args : Array<String>){
    MainLogger.log.info("I am in ur Kotlin Base killing ur .NET Server developers")

    val injector = Guice.createInjector(MarketDataServerModule())
    val server = injector.getInstance(MarketDataServer::class.java)

    server.Run()
}