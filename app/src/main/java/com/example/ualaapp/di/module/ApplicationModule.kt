package com.example.ualaapp.di.module

import android.content.Context
import androidx.room.Room
import com.example.ualaapp.data.repository.CityRepositoryImpl
import com.example.ualaapp.data.repository.FakeRepository
import com.example.ualaapp.data.room.Database
import com.example.ualaapp.data.service.ApiClient
import com.example.ualaapp.data.service.ApiServiceCity
import com.example.ualaapp.data.source.local.CityLocalSource
import com.example.ualaapp.data.source.local.CityLocalSourceImpl
import com.example.ualaapp.data.source.remote.CityRemoteSource
import com.example.ualaapp.data.source.remote.CityRemoteSourceImpl
import com.example.ualaapp.domain.repository.CityRepository
import com.example.ualaapp.domain.usecase.*
import com.example.ualaapp.helpers.Constants.BASE_URL
import com.example.ualaapp.helpers.NetworkHelper
import com.example.ualaapp.helpers.isRunningTest
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Module
class ApplicationModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideCityRepository(
        cityLocalSource: CityLocalSource,
        cityRemoteSource: CityRemoteSource
    ): CityRepository {
        return if (isRunningTest()) FakeRepository()
        else CityRepositoryImpl(cityRemoteSource, cityLocalSource)
    }

    @Provides
    @Singleton
    fun provideCityRemoteSource(cityRemoteSource: CityRemoteSourceImpl): CityRemoteSource =
        cityRemoteSource

    @Provides
    @Singleton
    fun provideGetAllCitiesUseCase(getAllCitiesUseCase: GetAllCitiesUseCaseImpl): GetAllCitiesUseCase =
        getAllCitiesUseCase

    @Provides
    @Singleton
    fun provideGetFavCitiesUseCase(getFavCitiesUseCase: GetFavCitiesUseCaseImpl): GetFavCitiesUseCase =
        getFavCitiesUseCase

    @Provides
    @Singleton
    fun provideGetCityUseCase(getCityUseCase: GetCityUseCaseImpl): GetCityUseCase =
        getCityUseCase

    @Provides
    @Singleton
    fun provideUpdateCityUseCase(updateCityUseCase: UpdateCityUseCaseImpl): UpdateCityUseCase =
        updateCityUseCase

    @Provides
    @Singleton
    fun provideApiServiceCity(): ApiServiceCity {
        return ApiClient.Builder<ApiServiceCity>()
            .setBaseUrl(BASE_URL)
            .setApiService(ApiServiceCity::class.java)
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database {
        return Room
            .databaseBuilder(context, Database::class.java, "UalaDB.db3")
            .build()
    }

    @Provides
    @Singleton
    fun provideCityLocalSource(database: Database): CityLocalSource {
        return CityLocalSourceImpl(database.cityDao())
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(context: Context) = NetworkHelper(context)

    @Provides
    @Singleton
    fun provideContext(): Context = context
}