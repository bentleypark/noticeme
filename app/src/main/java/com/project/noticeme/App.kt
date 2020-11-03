package com.project.noticeme

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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