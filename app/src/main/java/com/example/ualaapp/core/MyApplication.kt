package com.example.ualaapp.core

import android.app.Application
import com.example.ualaapp.di.component.ApplicationComponent
import com.example.ualaapp.di.component.DaggerApplicationComponent
import com.example.ualaapp.di.module.ApplicationModule

/**
 * @author Axel Sanchez
 */
class MyApplication: Application() {
    val component: ApplicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(ApplicationModule(this))
        .build()
}