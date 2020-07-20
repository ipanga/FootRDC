package com.tootiyesolutions.footrdc.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tootiyesolutions.footrdc.model.Article

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllNews(): LiveData<List<Article>>

    @Delete
    suspend fun deleteNews(article: Article)
}