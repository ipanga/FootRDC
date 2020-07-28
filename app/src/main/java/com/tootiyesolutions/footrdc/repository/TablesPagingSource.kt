package com.tootiyesolutions.footrdc.repository

import androidx.paging.PagingSource
import com.tootiyesolutions.footrdc.api.ApiService
import com.tootiyesolutions.footrdc.model.Tables
import com.tootiyesolutions.footrdc.util.Constants.Companion.NEWS_STARTING_PAGE_INDEX
import com.tootiyesolutions.footrdc.util.Constants.Companion.RESULTS_LEAGUES
import com.tootiyesolutions.footrdc.util.Constants.Companion.RESULTS_SEASONS
import retrofit2.HttpException
import java.io.IOException


class TablesPagingSource(
    private val service: ApiService
) : PagingSource<Int, Tables>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Tables> {
        val position = params.key ?: NEWS_STARTING_PAGE_INDEX
        return try {
            val response = service.getTables(RESULTS_LEAGUES, RESULTS_SEASONS, position)

            val tables = ArrayList(response.first().data.values) as List<Tables>

            LoadResult.Page(
                data = tables,
                prevKey = null,
                nextKey = null // Set to null since we just need to load only the first page
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}