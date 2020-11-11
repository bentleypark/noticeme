package com.project.noticeme.di

import android.content.Context
import androidx.room.Room
import com.project.noticeme.data.room.ConsumableDao
import com.project.noticeme.data.room.ConsumableDatabase
import com.project.noticeme.data.room.SearchHistoryDao
import com.project.noticeme.data.room.UserConsumableDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideConsumableDb(@ApplicationContext context: Context): ConsumableDatabase {
        return Room
            .databaseBuilder(
                context,
                ConsumableDatabase::class.java,
                ConsumableDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideConsumableDAO(consumableDatabase: ConsumableDatabase): ConsumableDao {
        return consumableDatabase.consumableDao()
    }


    @Singleton
    @Provides
    fun provideUserConsumableDao(consumableDatabase: ConsumableDatabase): UserConsumableDao {
        return consumableDatabase.userConsumableDao()
    }

    @Singleton
    @Provides
    fun provideSearchHistoryDao(consumableDatabase: ConsumableDatabase): SearchHistoryDao {
        return consumableDatabase.searchHistoryDao()
    }
}