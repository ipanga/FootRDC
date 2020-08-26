package com.tootiyesolutions.footrdc.repository

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import com.tootiyesolutions.footrdc.api.ApiService
import com.tootiyesolutions.footrdc.model.Result
import com.tootiyesolutions.footrdc.util.Constants.Companion.NEWS_ITEMS_PER_PAGE
import com.tootiyesolutions.footrdc.util.Constants.Companion.NEWS_STARTING_PAGE_INDEX
import com.tootiyesolutions.footrdc.util.Constants.Companion.RESULTS_LEAGUES
import com.tootiyesolutions.footrdc.util.Constants.Companion.RESULTS_SEASONS
import com.tootiyesolutions.footrdc.util.SharedPreferencesHelper
import retrofit2.HttpException
import java.io.IOException

class ResultsPagingSource(
    private val service: ApiService, context: Context
) : PagingSource<Int, Result>() {

    // Variable which will store in SharedPref time when the DB has been updated
    private var prefHelper = SharedPreferencesHelper(context)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: NEWS_STARTING_PAGE_INDEX
        return try {
            val apiResponse = service.getResults(RESULTS_LEAGUES, RESULTS_SEASONS, position, NEWS_ITEMS_PER_PAGE)

            // Filter matches that have been played to display as Results
            val response = if (position == 1) apiResponse.filter { it.resultStatus == "publish" } else apiResponse

            // Save Results for first page in preferences that will be used later as Fixtures after filtering only planned games
            if (position == 1) prefHelper.savePrefsResults(apiResponse.filter { it.resultStatus != "publish" })

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