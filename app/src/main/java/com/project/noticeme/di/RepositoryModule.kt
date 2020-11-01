package com.project.noticeme.di

import com.project.noticeme.data.api.ApiService
import com.project.noticeme.data.api.NetworkMapper
import com.project.noticeme.data.repository.MainRepository
import com.project.noticeme.data.room.ConsumableDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        consumableDao: ConsumableDao,
//        apiService: ApiService,
//        cacheMapper: CacheMapper,
//        networkMapper: NetworkMapper
    ): MainRepository {
        return MainRepository(consumableDao)
    }
}