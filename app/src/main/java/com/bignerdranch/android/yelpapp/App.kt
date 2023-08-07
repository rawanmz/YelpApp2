package com.bignerdranch.android.yelpapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App :  Application(), LifecycleObserver, Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    override fun onCreate() {
        super.onCreate()

        instance = this
        if (BuildConfig.DEBUG)
            //todo search for it
//        World.init(instance); // Initializes the libray and loads all data
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }
    companion object {

        private lateinit var instance: App
        var isAppInBackground: Boolean = true

        @JvmStatic
        fun getInstance() = instance
        private const val VALID = 0
        private const val INVALID = 1

    }

}