package com.example.aviasoft.data.persistence.pref

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException

class DatastoreImpl(private val context: Context) : PreferenceManager {

    private val Context.dataStore by preferencesDataStore("app_preferences")

    private val preferences = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }

    override suspend fun saveString(key: String, value: String) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[getStringPrefKey(key)] = value
        }
    }

    override suspend fun getString(key: String, value: String) =
        preferences.first()[getStringPrefKey(key)] ?: ""

    private fun getStringPrefKey(key: String) = stringPreferencesKey(key)

}