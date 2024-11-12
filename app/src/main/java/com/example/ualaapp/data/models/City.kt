package com.example.ualaapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class City(
    @PrimaryKey
    @SerializedName("_id")
    val id: Long? = null,
    val name: String? = null,
    val country: String? = null,
    @SerializedName("coord")
    val coordinates: Coordinates? = null,
    var fav: Boolean? = false
)
