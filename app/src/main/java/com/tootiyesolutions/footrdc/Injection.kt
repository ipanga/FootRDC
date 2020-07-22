package com.tootiyesolutions.footrdc

import androidx.lifecycle.ViewModelProvider
import com.tootiyesolutions.footrdc.api.ApiService
import com.tootiyesolutions.footrdc.repository.AppRepository
import com.tootiyesolutions.footrdc.ui.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
@ExperimentalCoroutinesApi
object Injection {

    /**
     * Creates an instance of [AppRepository] based on the [NewsService] and a
     * [NewsLocalCache]
     */
    private fun provideAppRepository(): AppRepository {
        return AppRepository(ApiService.create())
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideAppRepository())
    }
}