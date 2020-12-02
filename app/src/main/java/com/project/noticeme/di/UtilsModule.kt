package com.project.noticeme.di

import android.content.Context
import android.content.SharedPreferences
import com.project.noticeme.App
import com.project.noticeme.common.utils.preference.PreferenceUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object UtilsModule {

//    @Singleton
//    @Provides
//    fun provideAppContext(): Context {
//        return App.globalApplicationContext
//    }

    @Singleton
    @Provides
    fun providePreferenceUtil(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceUtil.getPref(context)
    }
}