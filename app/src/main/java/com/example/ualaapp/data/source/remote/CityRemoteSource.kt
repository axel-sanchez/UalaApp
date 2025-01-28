package com.example.ualaapp.data.source.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ualaapp.data.models.City
import com.example.ualaapp.data.models.DataCities
import com.example.ualaapp.data.service.ApiServiceCity
import com.example.ualaapp.helpers.Constants.ApiError.*
import com.example.ualaapp.helpers.NetworkHelper
import com.example.ualaapp.helpers.sortAlphabetically
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface CityRemoteSource {
    suspend fun getCities(): MutableLiveData<DataCities>
}

@Singleton
class CityRemoteSourceImpl @Inject constructor(private val service: ApiServiceCity,
                                               private val networkHelper: NetworkHelper
) : CityRemoteSource {
    override suspend fun getCities(): MutableLiveData<DataCities> {
        val mutableLiveData = MutableLiveData<DataCities>()

        try {
            if (!networkHelper.isOnline()) {
                mutableLiveData.value = DataCities(apiError = NETWORK_ERROR)
                return mutableLiveData
            }

            val response = service.getCities()
            if (response.isSuccessful) {
                Log.i("Successful Response", response.body().toString())

                response.body()?.let { result ->
                    mutableLiveData.value = DataCities(cities = sortAlphabetically(result))//.take(1000)))
                } ?: kotlin.run {
                    mutableLiveData.value = DataCities(apiError = GENERIC)
                }
            } else {
                Log.i("Error Response", response.errorBody().toString())
                val apiError = GENERIC
                apiError.error = response.message()
                mutableLiveData.value = DataCities(apiError = apiError)
            }
        } catch (e: IOException) {
            mutableLiveData.value = DataCities(apiError = GENERIC)
            Log.e(
                "CityRemoteSourceImpl",
                e.message?:"Error al obtener las ciudades"
            )
            e.printStackTrace()
        }

        return mutableLiveData
    }
}