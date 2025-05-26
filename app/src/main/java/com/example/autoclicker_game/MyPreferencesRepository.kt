package com.example.autoclicker_game

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create the single DataStore instance tied to Context here
private val Context.dataStore by preferencesDataStore(name = "points_store")

class MyPreferencesRepository(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val POINTS_KEY = intPreferencesKey("points")
    }

    val pointsFlow: Flow<Int> = dataStore.data
        .map { prefs -> prefs[POINTS_KEY] ?: 0 }

    suspend fun savePoints(points: Int) {
        dataStore.edit { prefs ->
            prefs[POINTS_KEY] = points
        }
    }
}
