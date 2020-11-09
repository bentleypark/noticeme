package com.project.noticeme.data.repository

import com.project.noticeme.data.room.ConsumableDao
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.room.UserConsumableDao
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.data.state.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository
constructor(
    private val consumableDao: ConsumableDao,
    private val userConsumableDao: UserConsumableDao
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

    suspend fun findConsumableWithTitle(title: String): Flow<DataState<ConsumableEntity>> =
        flow {

            emit(DataState.Loading)
            delay(1000)
            try {
                val result = consumableDao.findConsumableWithTitle(title)
                emit(DataState.Success(result))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }

    suspend fun insertUserConsumable(userConsumables: UserConsumableEntity): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading)
            delay(1000)
            try {
                userConsumableDao.insert(userConsumables)
                emit(DataState.Success(true))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }

    suspend fun getUserConsumable(): Flow<DataState<List<UserConsumableEntity>>> =
        flow {
            emit(DataState.Loading)
            delay(1000)
            try {
                val resultList = userConsumableDao.get()
                emit(DataState.Success(resultList))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }

    suspend fun delete(item: UserConsumableEntity): Flow<DataState<List<UserConsumableEntity>>> =
        flow {
            emit(DataState.Loading)
            delay(1000)
            try {
                userConsumableDao.delete(item)
//                emit(DataState.Success(true))
                val resultList = userConsumableDao.get()
                emit(DataState.Success(resultList))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }

    suspend fun deleteFromDetail(item: UserConsumableEntity): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading)
            delay(1000)
            try {
                userConsumableDao.delete(item)
                emit(DataState.Success(true))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }

    suspend fun getUserConsumableWithTitle(input: String): Flow<DataState<UserConsumableEntity>> =
        flow {
            emit(DataState.Loading)
            delay(1000)
            try {
                val resultList = userConsumableDao.getWithTitle(input)
                emit(DataState.Success(resultList))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }

    suspend fun update(item: UserConsumableEntity): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading)
            delay(1000)
            try {
                userConsumableDao.update(item)
                emit(DataState.Success(true))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }

    suspend fun insertCustomConsumable(consumable: ConsumableEntity): Flow<DataState<Boolean>> =
        flow {
            emit(DataState.Loading)
            delay(1000)

            try {
                consumableDao.insert(consumable)
                emit(DataState.Success(true))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }
}

