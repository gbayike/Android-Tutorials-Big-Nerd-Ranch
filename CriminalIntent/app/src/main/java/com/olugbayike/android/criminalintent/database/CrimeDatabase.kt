package com.olugbayike.android.criminalintent.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.olugbayike.android.criminalintent.Crime

@Database(
    version = 1,
    entities = [Crime::class]

)
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDatabase: RoomDatabase() {
    abstract fun crimeDao(): CrimeDao
}