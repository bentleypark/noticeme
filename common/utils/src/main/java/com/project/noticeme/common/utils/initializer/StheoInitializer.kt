package com.project.noticeme.common.utils.initializer

import android.content.Context
import androidx.startup.Initializer
import com.project.noticeme.common.utils.BuildConfig
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient

class StheoInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()
            Stetho.initializeWithDefaults(context)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
