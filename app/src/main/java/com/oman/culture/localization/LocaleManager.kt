package com.oman.culture.localization

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

class LocaleManager(private val context: Context) {
    
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    fun getLanguage(): String {
        return prefs.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
    }
    
    fun setLanguage(language: String) {
        prefs.edit().putString(KEY_LANGUAGE, language).apply()
    }
    
    fun isArabic(): Boolean = getLanguage() == ARABIC
    
    fun setLocale(context: Context): Context {
        return updateResources(context, getLanguage())
    }
    
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        
        return context.createConfigurationContext(config)
    }
    
    companion object {
        private const val PREFS_NAME = "locale_prefs"
        private const val KEY_LANGUAGE = "selected_language"
        private const val DEFAULT_LANGUAGE = "en"
        const val ENGLISH = "en"
        const val ARABIC = "ar"
    }
}
