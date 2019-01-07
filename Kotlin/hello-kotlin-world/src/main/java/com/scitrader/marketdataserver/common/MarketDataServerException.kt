package com.scitrader.marketdataserver.common

class MarketDataServerException : RuntimeException {

    constructor() : super() {}

    constructor(message: String) : super(message) {}

    constructor(message: String, inner: Exception) : super(message, inner) {}

    constructor(inner: Exception) : super(inner) {}
}