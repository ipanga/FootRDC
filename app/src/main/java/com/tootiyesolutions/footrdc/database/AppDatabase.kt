package com.tootiyesolutions.footrdc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tootiyesolutions.footrdc.Injection
import com.tootiyesolutions.footrdc.model.Article

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNewsDao(): AppDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        // The function below is called anytime we initialize or instantiate the following object ArticleDatabase()
        // and we set LOCK to prevent other multiple instant access to this bloc. And then we return the instance if
        // it's not null, other the database will be created
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "foot_rdc_app_db.db"
            ).build()
    }
}