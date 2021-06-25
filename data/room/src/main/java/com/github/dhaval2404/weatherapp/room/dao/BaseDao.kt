package com.github.dhaval2404.weatherapp.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 * Base DAO for all database entities
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
interface BaseDao<T> {

    /**
     * Insert list of entities
     *
     * @return list of entity ids
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: List<T>): List<Long>

    /**
     * Insert single entity
     *
     * @return entity id
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: T): Long

    /**
     * Update entity
     */
    @Update
    suspend fun update(entity: T): Int

    /**
     * Delete entity
     */
    @Delete
    suspend fun delete(entity: T): Int
}
