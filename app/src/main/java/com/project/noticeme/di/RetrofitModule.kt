package com.project.noticeme.di

import com.project.noticeme.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object RetrofitModule {

    @ViewModelScoped
    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        with(httpClient) {
            logger.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            logger.setLevel(HttpLoggingInterceptor.Level.BODY)
            addInterceptor(logger)
//            addInterceptor(AuthInterceptor())
        }
        httpClient.addInterceptor { chain: Interceptor.Chain ->
            val request: Request = chain.request().newBuilder()
                .header("UserBody-Agent", "android")
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }
        return httpClient.build()
    }

    @ViewModelScoped
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @ViewModelScoped
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): ApiService {
        return retrofit
            .build()
            .create(ApiService::class.java)
    }
}