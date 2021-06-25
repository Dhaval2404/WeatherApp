package com.github.dhaval2404.weatherapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.github.dhaval2404.weatherapp.room.entity.CityEntity

/**
 * City DAO
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
@Dao
interface CityDao : BaseDao<CityEntity> {

    @Query("select count(*) from city")
    suspend fun getCount(): Int

    @Query("select * from city")
    fun getAll(): LiveData<List<CityEntity>>

    @Query("select * from city")
    suspend fun getAllCities(): List<CityEntity>

    @Query("select * from city where id=:id")
    fun getById(id: Long): LiveData<CityEntity>

    @Query("delete from city")
    suspend fun clear()
}
