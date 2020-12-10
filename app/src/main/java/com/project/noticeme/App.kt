package com.project.noticeme

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = applicationContext

        if (BuildConfig.DEBUG) {
                Stetho.initializeWithDefaults(this@App)
            }
    }

    companion object {
        private lateinit var instance: Context

        val globalApplicationContext: Context
            get() = instance
    }
}