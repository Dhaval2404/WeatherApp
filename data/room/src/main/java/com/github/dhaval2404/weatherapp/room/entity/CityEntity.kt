package com.github.dhaval2404.weatherapp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Calendar

/**
 * City Database Table
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
@Entity(tableName = "city")
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "created_at")
    val createdAt: Calendar = Calendar.getInstance()
) : Serializable
