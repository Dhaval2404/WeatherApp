package com.github.dhaval2404.weatherapp.repository

import androidx.lifecycle.LiveData
import com.github.dhaval2404.weatherapp.room.dao.CityDao
import com.github.dhaval2404.weatherapp.room.entity.CityEntity

/**
 * City Repository
 *
 * Perform City Add/Delete/Get Operations
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
interface CityRepository {

    /**
     * Get all added cities
     */
    fun getAllCities(): LiveData<List<CityEntity>>

    /**
     * Add City
     */
    suspend fun insert(entity: CityEntity): Long

    /**
     * Delete City
     */
    suspend fun delete(entity: CityEntity): Int

    /**
     * Clear all city bookmarks
     */
    suspend fun clear()

}

class CityRepositoryImpl(private val cityDao: CityDao) : CityRepository {

    override suspend fun insert(entity: CityEntity) = cityDao.insert(entity)

    override suspend fun delete(entity: CityEntity) = cityDao.delete(entity)

    override fun getAllCities() = cityDao.getAll()

    override suspend fun clear() = cityDao.clear()
}
