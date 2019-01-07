package com.scitrader.marketdataserver

import org.apache.logging.log4j.LogManager

object MainLogger {
    val log = LogManager.getLogger(MainLogger::class.java)
}

fun main(args : Array<String>){
    MainLogger.log.info("I am in ur Kotlin Base killing ur .NET Server developers")
}