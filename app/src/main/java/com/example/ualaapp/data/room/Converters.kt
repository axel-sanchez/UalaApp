package com.example.ualaapp.data.room

import androidx.room.TypeConverter
import com.example.ualaapp.data.models.City
import com.example.ualaapp.data.models.Coordinates
import com.google.gson.Gson

/**
 * @author Axel Sanchez
 */
class Converters{
    private val gson: Gson = Gson()

    @TypeConverter
    fun fromCity(city: City?): String? {
        city?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toCity(city: String?): City? {
        city?.let {
            return gson.fromJson(it, City::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromCoordinates(coordinates: Coordinates?): String? {
        coordinates?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toCoordinates(coordinates: String?): Coordinates? {
        coordinates?.let {
            return gson.fromJson(it, Coordinates::class.java)
        } ?: return null
    }
}