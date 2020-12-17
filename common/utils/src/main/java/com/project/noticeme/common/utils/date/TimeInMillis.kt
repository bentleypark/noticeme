package com.project.noticeme.common.utils.date

import java.util.*

class TimeInMillis {
    fun getCurrentTimeMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.clear(Calendar.HOUR_OF_DAY)
        calendar.clear(Calendar.HOUR)
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)

        return calendar.timeInMillis
    }
}