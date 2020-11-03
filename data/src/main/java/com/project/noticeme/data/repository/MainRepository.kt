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
    suspend fun insertConsumable(consumables: List<ConsumableEntity>): Flow<DataState<String>> =

        flow {
            emit(DataState.Loading)
            delay(1000)

            try {
                for (consumable in consumables) {
                    consumableDao.insert(consumable)
                }
                emit(DataState.Success("작업이 완료되었습니다."))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }

    suspend fun findConsumableWithCategory(search: String): Flow<DataState<List<ConsumableEntity>>> =
        flow {

            emit(DataState.Loading)
            delay(1000)
            try {
                val resultList = consumableDao.findConsumableWithCategory(search)
                emit(DataState.Success(resultList))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }
}