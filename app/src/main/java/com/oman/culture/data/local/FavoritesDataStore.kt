package com.oman.culture.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "favorites")

class FavoritesDataStore(private val context: Context) {
    
    private val favoriteIdsKey = stringSetPreferencesKey("favorite_ids")
    
    val favoriteIds: Flow<Set<Int>> = context.dataStore.data.map { preferences ->
        preferences[favoriteIdsKey]?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()
    }
    
    suspend fun addFavorite(figureId: Int) {
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences[favoriteIdsKey] ?: emptySet()
            preferences[favoriteIdsKey] = currentFavorites + figureId.toString()
        }
    }
    
    suspend fun removeFavorite(figureId: Int) {
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences[favoriteIdsKey] ?: emptySet()
            preferences[favoriteIdsKey] = currentFavorites - figureId.toString()
        }
    }
    
    suspend fun toggleFavorite(figureId: Int) {
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences[favoriteIdsKey] ?: emptySet()
            val figureIdStr = figureId.toString()
            preferences[favoriteIdsKey] = if (figureIdStr in currentFavorites) {
                currentFavorites - figureIdStr
            } else {
                currentFavorites + figureIdStr
            }
        }
    }
    
    fun isFavorite(figureId: Int): Flow<Boolean> = context.dataStore.data.map { preferences ->
        val favorites = preferences[favoriteIdsKey] ?: emptySet()
        figureId.toString() in favorites
    }
    
    suspend fun clearAllFavorites() {
        context.dataStore.edit { preferences ->
            preferences[favoriteIdsKey] = emptySet()
        }
    }
}
