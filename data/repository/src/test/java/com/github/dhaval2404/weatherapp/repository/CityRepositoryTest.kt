package com.github.dhaval2404.weatherapp.repository

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.dhaval2404.weatherapp.room.dao.CityDao
import com.github.dhaval2404.weatherapp.room.entity.CityEntity
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class CityRepositoryTest {

    @RelaxedMockK
    private lateinit var cityDao: CityDao

    private lateinit var cityRepository: CityRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks

        cityRepository = CityRepositoryImpl(cityDao)
    }

    @Test
    fun `insert city`() {
        // When
        runBlocking {
            val city = CityEntity(
                address = "Surat, Gujarat - India",
                latitude = 21.1702,
                longitude = 72.8311
            )
            cityRepository.insert(city)

           coVerify { cityDao.insert(city) }
        }
    }

    @Test
    fun `delete city`() {
        runBlocking {
            val city = CityEntity(
                address = "Surat, Gujarat - India",
                latitude = 21.1702,
                longitude = 72.8311
            )
            // When
            cityRepository.delete(city)

            // Then
            coVerify { cityDao.delete(city) }
        }
    }

    @Test
    fun `get all cities`() {
        // When
        cityRepository.getAllCities()

        // Then
        verify { cityDao.getAll() }
    }

    @Test
    fun `clear city`() {
        runBlocking {
            // When
            cityRepository.clear()

            // Then
            coVerify { cityDao.clear() }
        }
    }

}