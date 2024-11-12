package com.example.ualaapp.di.component

import com.example.ualaapp.di.module.ApplicationModule
import com.example.ualaapp.navigation.Destinations
import com.example.ualaapp.presentation.view.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent{
    fun inject(citiesScreen: Destinations.CitiesScreen)
    fun inject(mainActivity: MainActivity)
}