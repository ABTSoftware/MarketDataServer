package com.scitrader.marketdataserver.common.utility

import com.scitrader.marketdataserver.common.ArgumentException
import com.scitrader.marketdataserver.common.ArgumentNullException

object Guard {

    fun assertNotNull(o: Any?, message: String) {
        if (o == null) {
            throw ArgumentNullException(message)
        }
    }

    fun assertIsTrue(expression: Boolean, message: String) {
        if (!expression) {
            throw ArgumentException(message)
        }
    }
}