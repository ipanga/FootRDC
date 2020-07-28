package com.tootiyesolutions.footrdc.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.tootiyesolutions.footrdc.model.Article
import com.tootiyesolutions.footrdc.model.Result
import com.tootiyesolutions.footrdc.model.Tables
import com.tootiyesolutions.footrdc.repository.AppRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * ViewModel for the [SearchNewsFragment] screen.
 * The ViewModel works with the [AppRepository] to get the data.
 */
@ExperimentalCoroutinesApi
class AppViewModel(private val repository: AppRepository) : ViewModel() {

    private var currentSearchNews: Flow<PagingData<UiModel>>? = null
    private var currentGamesResults: Flow<PagingData<Result>>? = null
    private var currentGamesFixtures: Flow<PagingData<Result>>? = null
    private var currentTeamsTables: Flow<PagingData<Tables>>? = null

    fun fetchNews(): Flow<PagingData<UiModel>> {

        val newResult: Flow<PagingData<UiModel>> = repository.getSearchNewsStream()
            .map { pagingData -> pagingData.map { UiModel.NewsItem(it) } }
            .map {
                it.insertSeparators<UiModel.NewsItem, UiModel> { before, after ->
                    if (after == null) {
                        // we're at the end of the list
                        return@insertSeparators null
                    }

                    if (before == null) {
                        // we're at the beginning of the list
                        // return@insertSeparators UiModel.SeparatorItem("${after.id}0.000+ stars")
                        return@insertSeparators null
                    }
                    // check between 2 items
                    if (before.id!! > after.id!!) {
                        if (after.id!! >= 1) {
                            UiModel.SeparatorItem("${after.id}0.000+ stars")
                        } else {
                            UiModel.SeparatorItem("< 10.000+ stars")
                        }
                    } else {
                        // no separator
                        null
                    }
                }
            }
            .cachedIn(viewModelScope)
        currentSearchNews = newResult
        return newResult
    }

    // Search for games results
    fun fetchResults(context: Context): Flow<PagingData<Result>> {
        val newResult: Flow<PagingData<Result>> = repository.getResultsStream(context)
            .cachedIn(viewModelScope)
        currentGamesResults = newResult
        return newResult
    }

    // Search for next fitures
    fun fetchFixtures(context: Context): Flow<PagingData<Result>> {
        val newFixtures: Flow<PagingData<Result>> = repository.getFixturesStream(context)
            .cachedIn(viewModelScope)
        currentGamesFixtures = newFixtures
        return newFixtures
    }

    // Search for teams tables
    fun fetchTables(): Flow<PagingData<Tables>> {
        val newTables: Flow<PagingData<Tables>> = repository.getTablesStream()
            .cachedIn(viewModelScope)
        currentTeamsTables = newTables
        return newTables
    }

}

sealed class UiModel {
    data class NewsItem(val news: Article) : UiModel()
    data class SeparatorItem(val description: String) : UiModel()
}

private val UiModel.NewsItem.id: Int?
    get() = this.news.id?.div(30)
