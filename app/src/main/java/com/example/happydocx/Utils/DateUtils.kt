package com.example.happydocx.Utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

object DateUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatAppointmentDate(isoDate: String): String {
        return try {
            // For Android API 26+ (Use this if minSdk >= 26)
            val zonedDateTime = ZonedDateTime.parse(isoDate)
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy  hh:mm a")
            zonedDateTime.format(formatter)

            // Output: "05 Nov 2025  05:56 AM"
        } catch (e: Exception) {
            isoDate  // Return original if parsing fails
        }
    }

    // For older Android versions (API < 26)
    fun formatAppointmentDateLegacy(isoDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(isoDate)

            val outputFormat = SimpleDateFormat("dd MMM yyyy  hh:mm a", Locale.getDefault())
            outputFormat.format(date ?: return isoDate)
        } catch (e: Exception) {
            isoDate
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun gettingOnlyDate(isoDate: String): String {
        return try {
            val localDate = LocalDate.parse(isoDate.substringBefore('T'))  // Extracts "2025-11-05"
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
            localDate.format(formatter)
        } catch (e: Exception) {
            isoDate
        }
    }
}

