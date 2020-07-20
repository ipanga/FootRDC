package com.tootiyesolutions.footrdc.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tootiyesolutions.footrdc.repository.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Factory for ViewModels
 */
@ExperimentalCoroutinesApi
class ViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchNewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchNewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}