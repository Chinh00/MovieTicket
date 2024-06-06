package com.superman.movieticket.infrastructure.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DatetimeHelper {
    @RequiresApi(Build.VERSION_CODES.O)
    fun ConvertISODatetimeToLocalDatetime(dateString: String, formatterPattern: String = "dd/MM/yyyy HH:mm:ss"): String {
        val zonedDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
        val formatter = DateTimeFormatter.ofPattern(formatterPattern)

        return zonedDateTime.format(formatter)
    }
}

