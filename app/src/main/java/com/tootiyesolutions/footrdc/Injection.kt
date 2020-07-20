package com.tootiyesolutions.footrdc

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.tootiyesolutions.footrdc.api.ApiService
import com.tootiyesolutions.footrdc.database.AppDatabase
import com.tootiyesolutions.footrdc.repository.NewsRepository
import com.tootiyesolutions.footrdc.ui.ViewModelFactory

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    /**
     * Creates an instance of [NewsRepository] based on the [NewsService] and a
     * [NewsLocalCache]
     */
    private fun provideNewsRepository(context: Context): NewsRepository {
        return NewsRepository(ApiService.create(), AppDatabase.invoke(context))
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideNewsRepository(context))
    }
}