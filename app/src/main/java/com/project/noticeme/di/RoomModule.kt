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
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class RoomModule {

    @ViewModelScoped
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

    @ViewModelScoped
    @Provides
    fun provideConsumableDAO(consumableDatabase: ConsumableDatabase): ConsumableDao {
        return consumableDatabase.consumableDao()
    }


    @ViewModelScoped
    @Provides
    fun provideUserConsumableDao(consumableDatabase: ConsumableDatabase): UserConsumableDao {
        return consumableDatabase.userConsumableDao()
    }

    @ViewModelScoped
    @Provides
    fun provideSearchHistoryDao(consumableDatabase: ConsumableDatabase): SearchHistoryDao {
        return consumableDatabase.searchHistoryDao()
    }
}