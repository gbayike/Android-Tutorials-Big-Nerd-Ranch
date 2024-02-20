package com.olugbayike.android.criminalintent.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

class CrimeTypeConverters {

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(milliSinceEpoch: Long): Date {
        return Date(milliSinceEpoch)
    }
}