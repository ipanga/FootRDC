package com.tootiyesolutions.footrdc.api

import com.tootiyesolutions.footrdc.model.Article
import com.tootiyesolutions.footrdc.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("wp-json/wp/v2/posts")
    suspend fun getBreakingNews(
        @Query("page") pageNumber: Int,
        @Query("per_page") newsPerPage: Int
    ): List<Article>

    @GET("wp-json/wp/v2/posts")
    suspend fun searchForNews(
        @Query("search") searchQuery: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") newsPerPage: Int
    ): List<Article>

    companion object {

        fun create(): ApiService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}