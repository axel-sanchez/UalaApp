package com.example.ualaapp.data.room

import androidx.room.*
import com.example.ualaapp.data.models.City

/**
 * @author Axel Sanchez
 */
@Dao
interface CityDao {
    @Query("SELECT * FROM City WHERE name LIKE :name ORDER BY name ASC")
    suspend fun getCitiesByName(name: String): List<City>

    @Query("SELECT * FROM City ORDER BY name ASC")
    suspend fun getCities(): List<City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: City): Long

    @Update
    suspend fun updateCity(city: City)
}