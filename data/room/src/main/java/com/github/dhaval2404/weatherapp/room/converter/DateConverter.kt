package com.github.dhaval2404.weatherapp.room.converter

import androidx.room.TypeConverter
import java.util.Calendar

/**
 * Room Date Converter
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
object DateConverter {

    /**
     * Convert time in millis to Calendar
     */
    @JvmStatic
    @TypeConverter
    fun toCalendar(timeInMillis: Long?): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis ?: 0
        return calendar
    }

    /**
     * Convert Calendar to time in millis
     */
    @JvmStatic
    @TypeConverter
    fun fromCalendar(calendar: Calendar?): Long {
        return calendar?.timeInMillis ?: 0
    }
}
