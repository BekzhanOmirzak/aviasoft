package com.example.aviasoft.data.persistence.pref

interface PreferenceManager {

    suspend fun saveString(key: String, value: String)

    suspend fun getString(key: String, value: String): String

    companion object {
        const val CONFIGURATION_KEY = "CONFIGURATION_KEY"
    }
}