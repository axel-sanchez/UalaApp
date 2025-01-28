package com.example.ualaapp.data.room

import androidx.room.*
import com.example.ualaapp.data.models.City

/**
 * @author Axel Sanchez
 */
@Dao
interface CityDao {

    @Query("SELECT * FROM City ORDER BY name ASC")
    suspend fun getCities(): List<City>

    @Query("SELECT * FROM City WHERE fav = 1 ORDER BY name ASC")
    suspend fun getFavCities(): List<City>

    @Query("SELECT * FROM City WHERE id = :idCity")
    suspend fun getCity(idCity: Long): City

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: City): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<City>)

    @Update
    suspend fun updateCity(city: City)
}