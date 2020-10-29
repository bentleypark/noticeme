package com.project.noticeme.data.repository

import com.project.noticeme.data.api.ApiService
import com.project.noticeme.data.api.NetworkMapper
import com.project.noticeme.data.model.Blog
import com.project.noticeme.data.state.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository
constructor(
//    private val blogDao: BlogDao,
    private val apiService: ApiService,
//    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun getBlogs(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkBlogs = apiService.get()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
//            for (blog in blogs) {
//                blogDao.insert(cacheMapper.mapToEntity(blog))
//            }
//            val cachedBlogs = blogDao.get()
            emit(DataState.Success(blogs))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}