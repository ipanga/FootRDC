package com.tootiyesolutions.footrdc.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tootiyesolutions.footrdc.api.ApiService
import com.tootiyesolutions.footrdc.database.AppDatabase
import com.tootiyesolutions.footrdc.model.Article
import com.tootiyesolutions.footrdc.util.Constants.Companion.NEWS_ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

/**
 * Repository class that works with local and remote data sources.
 */
class NewsRepository(private val service: ApiService, private val database: AppDatabase) {
    /**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    fun getSearchResultStream(query: String): Flow<PagingData<Article>> {
        Log.d("NewsRepository", "New query: $query")
        return Pager(
            config = PagingConfig(pageSize = NEWS_ITEMS_PER_PAGE, enablePlaceholders = false),
            pagingSourceFactory = { NewsPagingSource(service, query) }
        ).flow
    }

    suspend fun upsert(article: Article) = database.getNewsDao().upsert(article)

    fun getSavedNews() = database.getNewsDao().getAllNews()

    suspend fun deleteNews(article: Article) = database.getNewsDao().deleteNews(article)
}