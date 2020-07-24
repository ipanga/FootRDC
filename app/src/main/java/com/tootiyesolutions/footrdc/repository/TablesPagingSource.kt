package com.tootiyesolutions.footrdc.repository

import android.util.Log
import androidx.paging.PagingSource
import com.tootiyesolutions.footrdc.api.ApiService
import com.tootiyesolutions.footrdc.model.Tables
import com.tootiyesolutions.footrdc.util.Constants
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
            Log.d("VALYNK", "Before collecting response")
            val response = service.getTables(RESULTS_LEAGUES, RESULTS_SEASONS, position)
            Log.d("VALYNK", "After collecting response")

            Log.d("VALYNK Valeur", response.first().data.values.toString())
            // val tables = response.first().data.values.toList()
            val tables = ArrayList(response.first().data.values) as List<Tables>
            Log.d("VALYNK", "After collecting response and before parsing")

            LoadResult.Page(
                data = tables,
                prevKey = if (position == NEWS_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (tables.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}