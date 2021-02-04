package com.project.noticeme.common.utils.di

import android.content.Context
import com.project.noticeme.common.utils.date.TimeInMillis
import com.project.noticeme.common.utils.preference.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): SharedPreferenceManager =
        SharedPreferenceManager(
            context.getSharedPreferences(
                UtilsModule.javaClass.name,
                Context.MODE_PRIVATE
            )
        )

    @Provides
    fun provideTimeMillis() = TimeInMillis()
}