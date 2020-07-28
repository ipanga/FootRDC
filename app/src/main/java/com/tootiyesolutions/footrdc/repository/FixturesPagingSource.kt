package com.tootiyesolutions.footrdc.repository

import android.content.Context
import androidx.paging.PagingSource
import com.tootiyesolutions.footrdc.model.Result
import com.tootiyesolutions.footrdc.util.SharedPreferencesHelper
import retrofit2.HttpException
import java.io.IOException

class FixturesPagingSource(
    context: Context
) : PagingSource<Int, Result>() {

    // Variable which will store in SharedPref time when the DB has been updated
    private var prefHelper = SharedPreferencesHelper(context)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val response = prefHelper.getPrefsResults()

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = null
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}