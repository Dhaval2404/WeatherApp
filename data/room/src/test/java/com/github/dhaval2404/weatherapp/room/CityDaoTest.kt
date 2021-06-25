package com.github.dhaval2404.weatherapp.room

import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.dhaval2404.weatherapp.room.dao.CityDao
import com.github.dhaval2404.weatherapp.room.entity.CityEntity
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class CityDaoTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var cityDao: CityDao

    @Before
    fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .build()

        cityDao = appDatabase.cityDao()
    }

    @After
    fun closeDatabase() {
        appDatabase.close()
    }

    @Test
    fun `insert one city and db count must be 1`() {
        // When
        runBlocking {
            val city = CityEntity(
                address = "Surat, Gujarat - India",
                latitude = 21.1702,
                longitude = 72.8311,
            )
            cityDao.insert(city)

            // Then
            val count = cityDao.getCount()
            Truth.assertThat(count).isEqualTo(1)
        }
    }

    @Test
    fun `insert two cities and db count must be 2`() {
        // When
        runBlocking {
            cityDao.insert(
                CityEntity(
                    address = "Surat, Gujarat - India",
                    latitude = 21.1702,
                    longitude = 72.8311,
                )
            )

            cityDao.insert(
                CityEntity(
                    address = "Vadodara, Gujarat - India",
                    latitude = 22.3072,
                    longitude = 73.1812,
                )
            )

            // Then
            val count = cityDao.getCount()
            Truth.assertThat(count).isEqualTo(2)
        }
    }

    @Test
    fun `insert two cities and get both`() {
        // When
        runBlocking {
            cityDao.insert(
                CityEntity(
                    address = "Surat, Gujarat - India",
                    latitude = 21.1702,
                    longitude = 72.8311,
                )
            )

            cityDao.insert(
                CityEntity(
                    address = "Vadodara, Gujarat - India",
                    latitude = 21.1702,
                    longitude = 72.8311,
                )
            )

            // Then
            val cities = cityDao.getAllCities()
            Truth.assertThat(cities.size).isEqualTo(2)
        }
    }

    @Test
    fun `insert multiple cities and then delete all finally count must be zero`() {
        // When
        runBlocking {
            cityDao.insert(
                CityEntity(
                    address = "Surat, Gujarat - India",
                    latitude = 21.1702,
                    longitude = 72.8311,
                )
            )

            cityDao.insert(
                CityEntity(
                    address = "Vadodara, Gujarat - India",
                    latitude = 21.1702,
                    longitude = 72.8311,
                )
            )

            cityDao.insert(
                CityEntity(
                    address = "Valsad, Gujarat - India",
                    latitude = 20.5992,
                    longitude = 72.9342,
                )
            )

            // Then
            var cities = cityDao.getAllCities()
            Truth.assertThat(cities.size).isEqualTo(3)

            // Then
            cityDao.clear()

            // Then
            cities = cityDao.getAllCities()
            Truth.assertThat(cities.size).isEqualTo(0)
        }
    }

    @Test
    fun `when is empty count must be 0`() {
        runBlocking {
            // When
            val count = cityDao.getCount()

            // Then
            Truth.assertThat(count).isEqualTo(0)
        }
    }

}