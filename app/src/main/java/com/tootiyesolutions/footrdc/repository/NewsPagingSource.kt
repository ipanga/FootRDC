package com.tootiyesolutions.footrdc.repository

import androidx.paging.PagingSource
import com.tootiyesolutions.footrdc.api.ApiService
import com.tootiyesolutions.footrdc.model.Article
import com.tootiyesolutions.footrdc.util.Constants.Companion.GET_BREAKING_NEWS
import com.tootiyesolutions.footrdc.util.Constants.Companion.NEWS_ITEMS_PER_PAGE
import com.tootiyesolutions.footrdc.util.Constants.Companion.NEWS_STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource(
    private val service: ApiService,
    private val query: String
) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: NEWS_STARTING_PAGE_INDEX
        val apiQuery = query
        return try {
            val response = if (query == GET_BREAKING_NEWS) {
                service.getBreakingNews(position, NEWS_ITEMS_PER_PAGE)
            } else {
                service.searchForNews(apiQuery, position, NEWS_ITEMS_PER_PAGE)
            }

            LoadResult.Page(
                data = response,
                prevKey = if (position == NEWS_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}