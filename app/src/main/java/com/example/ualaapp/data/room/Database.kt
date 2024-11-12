package com.example.ualaapp.data.room

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Database
import com.example.ualaapp.data.models.City
import com.example.ualaapp.data.models.Coordinates

/**
 * @author Axel Sanchez
 */
@Database(
    entities = [City::class, Coordinates::class],
    version = 3
)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase() {
    abstract fun cityDao(): CityDao
}