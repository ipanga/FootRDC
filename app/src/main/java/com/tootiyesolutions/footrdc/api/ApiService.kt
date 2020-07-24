package com.tootiyesolutions.footrdc.api

import com.tootiyesolutions.footrdc.model.Article
import com.tootiyesolutions.footrdc.model.Result
import com.tootiyesolutions.footrdc.model.TablesItem
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

    @GET("wp-json/sportspress/v2/events")
    suspend fun getResults(
        @Query("leagues") leaguesId: Int,
        @Query("seasons") seasonsId: Int,
        @Query("page") pageNumber: Int,
        @Query("per_page") resultsPerPage: Int
    ): List<Result>

    @GET("wp-json/sportspress/v2/tables")
    suspend fun getTables(
        @Query("leagues") leaguesId: Int,
        @Query("seasons") seasonsId: Int,
        @Query("page") pageNumber: Int
    ): List<TablesItem>

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