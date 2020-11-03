package com.project.noticeme

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = applicationContext
    }

    companion object {
        private lateinit var instance: Context

        val globalApplicationContext: Context
            get() = instance
    }
}