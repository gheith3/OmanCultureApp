package com.oman.culture.ui.screens.onboarding

import android.content.Context
import android.content.SharedPreferences

class OnboardingPreferences(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )
    
    var isOnboardingCompleted: Boolean
        get() = prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
        set(value) = prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, value).apply()
    
    var selectedLanguage: String
        get() = prefs.getString(KEY_SELECTED_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
        set(value) = prefs.edit().putString(KEY_SELECTED_LANGUAGE, value).apply()
    
    fun completeOnboarding() {
        isOnboardingCompleted = true
    }
    
    fun resetOnboarding() {
        isOnboardingCompleted = false
    }
    
    companion object {
        private const val PREFS_NAME = "oman_culture_onboarding"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
        private const val KEY_SELECTED_LANGUAGE = "selected_language"
        private const val DEFAULT_LANGUAGE = "en"
    }
}
