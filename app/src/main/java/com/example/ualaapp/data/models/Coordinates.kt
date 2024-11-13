package com.example.ualaapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Coordinates(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val lat: Double? = null,
    val lon: Double? = null
)
