package com.superman.movieticket.infrastructure.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

 object DatetimeHelper {
    @RequiresApi(Build.VERSION_CODES.O)
    fun ConvertISODatetimeToLocalDatetime(dateString: String, formatterPattern: String = "dd/MM/yyyy HH:mm:ss"): String {
        val zonedDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
        val formatter = DateTimeFormatter.ofPattern(formatterPattern)

        return zonedDateTime.format(formatter)
    }
     @RequiresApi(Build.VERSION_CODES.O)
     fun convertIsoToTime(isoDateTime: String,formatterPattern: String="HH:mm"): String {
         return try {
             val inputFormat = DateTimeFormatter.ISO_ZONED_DATE_TIME
             val outputFormat = DateTimeFormatter.ofPattern(formatterPattern)

             val dateTime = ZonedDateTime.parse(isoDateTime, inputFormat)
                 .withZoneSameInstant(ZoneId.systemDefault()) // Convert to local time zone

             dateTime.format(outputFormat)
         } catch (e: Exception) {
             e.printStackTrace()
             ""
         }
     }
}

