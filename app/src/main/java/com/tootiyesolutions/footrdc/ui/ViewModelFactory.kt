package com.tootiyesolutions.footrdc.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tootiyesolutions.footrdc.repository.AppRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Factory for ViewModels
 */
@ExperimentalCoroutinesApi
class ViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
