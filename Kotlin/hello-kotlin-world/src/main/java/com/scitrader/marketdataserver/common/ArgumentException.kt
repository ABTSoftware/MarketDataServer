package com.scitrader.marketdataserver.common

open class ArgumentException : RuntimeException {
    constructor() : super() {}

    constructor(message: String) : super(message) {}

    constructor(message: String, inner: Exception) : super(message, inner) {}

    constructor(inner: Exception) : super(inner) {}
}

