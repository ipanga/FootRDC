package com.tootiyesolutions.footrdc.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tootiyesolutions.footrdc.model.Result

class SharedPreferencesHelper {
    // Static variables available in all classes
    companion object{

        // Variable which will store time we update de DB
        private const val PREF_TIME = "Pref time"

        // Variable which will store time we update de DB
        private const val PREF_RESULTS = "Pref results"

        private var prefs: SharedPreferences? = null

        // Singleton pattern to allow only one thread to access the database at time
        @Volatile private var instance: SharedPreferencesHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPreferencesHelper = instance ?: synchronized(LOCK){
            instance ?: buildHelper(context).also{
                instance = it
            }
        }

        private fun buildHelper(context: Context) : SharedPreferencesHelper{
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }

    fun saveUpdateTime(time: Long){
        prefs?.edit(commit = true) {putLong(PREF_TIME, time)}
    }

    fun getUpdateTime() = prefs?.getLong(PREF_TIME, 0)

    fun savePrefsResults(listResults: List<Result>){
        val gson = Gson()
        val json = gson.toJson(listResults)//converting list to Json
        prefs?.edit(commit = true) {putString(PREF_RESULTS, json)}
    }

    fun getPrefsResults(): List<Result>{
        val gson = Gson()
        val json = prefs?.getString(PREF_RESULTS, null)
        val type = object : TypeToken<List<Result>>(){}.type //converting the json to list
        return gson.fromJson(json,type)//returning the list
    }

    // fun getPrefsResults() = prefs?.getString(PREF_RESULTS, null)
}