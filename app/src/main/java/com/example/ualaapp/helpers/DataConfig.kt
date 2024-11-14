package com.example.ualaapp.helpers

import androidx.test.platform.app.InstrumentationRegistry

/**
 * @author Axel Sanchez
 */
fun isRunningTest(): Boolean{
    return try {
        InstrumentationRegistry.getInstrumentation()
        true
    } catch (e: IllegalStateException) {
        false
    }
}