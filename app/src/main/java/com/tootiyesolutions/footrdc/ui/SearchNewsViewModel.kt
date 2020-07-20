package com.tootiyesolutions.footrdc.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.tootiyesolutions.footrdc.model.Article
import com.tootiyesolutions.footrdc.repository.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * ViewModel for the [SearchNewsFragment] screen.
 * The ViewModel works with the [NewsRepository] to get the data.
 */
@ExperimentalCoroutinesApi
class SearchNewsViewModel(private val repository: NewsRepository) : ViewModel() {
    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<UiModel>>? = null

    fun searchRepo(queryString: String): Flow<PagingData<UiModel>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<UiModel>> = repository.getSearchResultStream(queryString)
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
        currentSearchResult = newResult
        return newResult
    }

    fun saveNews(article: Article) = viewModelScope.launch {
        repository.upsert(article)
    }

    fun getSavedNews() = repository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteNews(article)
    }

}

sealed class UiModel {
    data class NewsItem(val news: Article) : UiModel()
    data class SeparatorItem(val description: String) : UiModel()
}

private val UiModel.NewsItem.id: Int?
    get() = this.news.id?.div(30)
