package com.scitrader.marketdataserver.common.Utility

import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.chrono.ISOChronology
import org.joda.time.format.DateTimeFormat
import java.util.*

object DateUtil {

    val tickFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ").withLocale(Locale.US)
            .withChronology(ISOChronology.getInstanceUTC())

    fun RoundDown(value: DateTime, nearest: Duration): DateTime {
        return DateTime(RoundDown(value.millis, nearest.millis))
    }

    fun RoundUp(value: DateTime, nearest: Duration): DateTime {
        return DateTime(RoundUp(value.millis, nearest.millis))
    }

    private fun RoundDown(value: Long, nearest: Long): Long {
        return (Math.floor((value / nearest).toDouble()) * nearest).toLong()
    }

    private fun RoundUp(value: Long, nearest: Long): Long {
        return (Math.ceil((value / nearest).toDouble()) * nearest).toLong()
    }
}