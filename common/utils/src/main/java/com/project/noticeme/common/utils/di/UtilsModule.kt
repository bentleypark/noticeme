package com.project.noticeme.common.utils.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.project.noticeme.common.utils.date.TimeInMillis
import com.project.noticeme.common.utils.preference.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object UtilsModule {

//    @Provides
//    fun provideDefaultSharedPreferences(
//        @ApplicationContext context: Context
//    ): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): SharedPreferenceManager =
        SharedPreferenceManager(
            context.getSharedPreferences(
                UtilsModule.javaClass.name,
                Context.MODE_PRIVATE
            )
        )

    @Provides
    fun provideTimemillis() = TimeInMillis()
}