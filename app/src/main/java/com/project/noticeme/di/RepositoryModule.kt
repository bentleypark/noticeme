package com.project.noticeme.di

import com.project.noticeme.data.repository.MainRepository
import com.project.noticeme.data.room.ConsumableDao
import com.project.noticeme.data.room.SearchHistoryDao
import com.project.noticeme.data.room.UserConsumableDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideMainRepository(
        consumableDao: ConsumableDao,
        userConsumableDao: UserConsumableDao,
        searchHistoryDao: SearchHistoryDao
    ): MainRepository {
        return MainRepository(consumableDao, userConsumableDao, searchHistoryDao)
    }
}