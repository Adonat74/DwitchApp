package com.example.dwitchapp

import android.app.Application
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Timber.d("Debug message")
        Timber.e("Error message")
        Timber.w("Warning message")
        Timber.i("Info message")
        Timber.v("Verbose message")
    }
}

