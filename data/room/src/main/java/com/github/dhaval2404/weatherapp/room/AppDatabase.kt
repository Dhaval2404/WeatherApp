package com.github.dhaval2404.weatherapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.dhaval2404.weatherapp.room.converter.DateConverter
import com.github.dhaval2404.weatherapp.room.dao.CityDao
import com.github.dhaval2404.weatherapp.room.entity.CityEntity

@Database(
    version = 1,
    entities = [
        CityEntity::class
    ],
    exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        /**
         * Get Room Database
         */
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "weather.db")
                .build()
        }
    }

    /**
     * Get City DAO
     *
     * @return CityDao
     */
    abstract fun cityDao(): CityDao
}
