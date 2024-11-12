package com.example.ualaapp.helpers

import android.content.Context
import android.net.ConnectivityManager
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 * */
@Singleton
class NetworkHelper @Inject constructor(private val context: Context) {
    fun isOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = Objects.requireNonNull(cm).activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}