package com.bentley.data.api

import com.bentley.data.repository.BlogNetworkEntity
import retrofit2.http.GET

interface ApiService {

    companion object {
        const val BASE_URL = "https://open-api.xyz/placeholder/"
    }

    @GET("blogs")
    suspend fun get(): List<BlogNetworkEntity>
}