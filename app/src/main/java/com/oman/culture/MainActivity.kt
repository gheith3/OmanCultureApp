package com.oman.culture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.oman.culture.data.local.FavoritesDataStore
import com.oman.culture.ui.screens.onboarding.OnboardingPreferences
import com.oman.culture.ui.theme.OmanCultureTheme

class MainActivity : ComponentActivity() {
    
    private lateinit var onboardingPreferences: OnboardingPreferences
    private lateinit var favoritesDataStore: FavoritesDataStore
    private var isOnboardingCompleted by mutableStateOf(false)
    private var isArabic by mutableStateOf(false)
    private var isDarkMode by mutableStateOf(false)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        onboardingPreferences = OnboardingPreferences(this)
        favoritesDataStore = FavoritesDataStore(this)
        isOnboardingCompleted = onboardingPreferences.isOnboardingCompleted
        isArabic = onboardingPreferences.selectedLanguage == "ar"
        
        setContent {
            OmanCultureTheme(darkTheme = isDarkMode) {
                OmanCultureApp(
                    isOnboardingCompleted = isOnboardingCompleted,
                    isArabic = isArabic,
                    isDarkMode = isDarkMode,
                    favoritesDataStore = favoritesDataStore,
                    onOnboardingComplete = {
                        onboardingPreferences.completeOnboarding()
                        isOnboardingCompleted = true
                    },
                    onLanguageSelected = { language ->
                        onboardingPreferences.selectedLanguage = language
                        isArabic = language == "ar"
                    },
                    onLanguageChange = { arabic ->
                        isArabic = arabic
                        onboardingPreferences.selectedLanguage = if (arabic) "ar" else "en"
                    },
                    onThemeChange = { dark ->
                        isDarkMode = dark
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    OmanCultureTheme {
        OmanCultureApp(isOnboardingCompleted = true)
    }
}