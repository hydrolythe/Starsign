package com.example.starsign

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class PusherApplication : Application(){

    override fun onCreate(){
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(mutableListOf(appModule, userModule))
        }
    }
}