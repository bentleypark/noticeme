package com.project.noticeme.data.repository

import com.project.noticeme.data.room.ConsumableDao
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.state.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class MainRepository
constructor(
    private val consumableDao: ConsumableDao,
//    private val apiService: ApiService,
//    private val cacheMapper: CacheMapper,
//    private val networkMapper: NetworkMapper
) {
    suspend fun insertConsumable(consumables: List<ConsumableEntity>) {
//            : Flow<DataState<List<ConsumableEntity>>> =
//        flow {
//        emit(DataState.Loading)
        delay(1000)
        try {
//            val networkBlogs = apiService.get()
//            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for (consumable in consumables) {
                consumableDao.insert(consumable)
            }
            val cachedBlogs = consumableDao.get()
            Timber.d("${cachedBlogs[0].title}")
//            emit(DataState.Success(cachedBlogs))
        } catch (e: Exception) {
//            emit(DataState.Error(e))
        }
    }

}