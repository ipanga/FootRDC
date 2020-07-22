package com.tootiyesolutions.footrdc.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tootiyesolutions.footrdc.api.ApiService
import com.tootiyesolutions.footrdc.model.Article
import com.tootiyesolutions.footrdc.model.Result
import com.tootiyesolutions.footrdc.util.Constants.Companion.NEWS_ITEMS_PER_PAGE
import com.tootiyesolutions.footrdc.util.Constants.Companion.VISIBLE_THRESHOLD
import kotlinx.coroutines.flow.Flow

/**
 * Repository class that works with local and remote data sources.
 */
class AppRepository(private val service: ApiService) {
    /**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    fun getSearchNewsStream(): Flow<PagingData<Article>> {
        Log.d("AppRepository", "New Search for BreakingNews")
        return Pager(
            config = PagingConfig(pageSize = NEWS_ITEMS_PER_PAGE, prefetchDistance = VISIBLE_THRESHOLD,enablePlaceholders = false),
            pagingSourceFactory = { NewsPagingSource(service) }
        ).flow
    }

    fun getSearchResultsStream(): Flow<PagingData<Result>> {
        Log.d("AppRepository", "New Search for Results")
        return Pager(
            config = PagingConfig(pageSize = NEWS_ITEMS_PER_PAGE, prefetchDistance = VISIBLE_THRESHOLD,enablePlaceholders = false),
            pagingSourceFactory = { ResultsPagingSource(service) }
        ).flow
    }
}