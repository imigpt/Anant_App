package com.example.anantapp.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.anantapp.ui.theme.ScreenTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "theme_preferences")
private val CURRENT_THEME_KEY = stringPreferencesKey("current_theme")

/**
 * Theme Repository - Manages theme persistence
 * Saves and retrieves theme preference from DataStore
 */
class ThemeRepository(private val context: Context) {
    
    val currentTheme: Flow<ScreenTheme> = context.dataStore.data.map { preferences ->
        val themeName = preferences[CURRENT_THEME_KEY] ?: ScreenTheme.PROFILE.name
        try {
            ScreenTheme.valueOf(themeName)
        } catch (e: IllegalArgumentException) {
            ScreenTheme.PROFILE
        }
    }
    
    suspend fun setCurrentTheme(theme: ScreenTheme) {
        context.dataStore.edit { preferences ->
            preferences[CURRENT_THEME_KEY] = theme.name
        }
    }
    
    suspend fun resetToDefaultTheme() {
        context.dataStore.edit { preferences ->
            preferences[CURRENT_THEME_KEY] = ScreenTheme.PROFILE.name
        }
    }
}
