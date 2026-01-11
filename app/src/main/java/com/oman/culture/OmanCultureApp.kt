package com.oman.culture

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oman.culture.data.local.FavoritesDataStore
import com.oman.culture.ui.components.AnimatedBottomBar
import com.oman.culture.ui.navigation.NavGraph
import com.oman.culture.ui.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun OmanCultureApp(
    isOnboardingCompleted: Boolean = false,
    isArabic: Boolean = false,
    isDarkMode: Boolean = false,
    favoritesDataStore: FavoritesDataStore? = null,
    onOnboardingComplete: () -> Unit = {},
    onLanguageSelected: (String) -> Unit = {},
    onLanguageChange: (Boolean) -> Unit = {},
    onThemeChange: (Boolean) -> Unit = {}
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scope = rememberCoroutineScope()
    
    // Favorites from DataStore
    val favoriteIds by favoritesDataStore?.favoriteIds?.collectAsState(initial = emptySet()) 
        ?: androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(emptySet<Int>()) }
    
    // Determine start destination based on onboarding status
    val startDestination = if (isOnboardingCompleted) {
        Screen.Home.route
    } else {
        Screen.Onboarding.route
    }
    
    // Routes where bottom bar should be visible
    val bottomBarRoutes = listOf(
        Screen.Home.route,
        Screen.Favorites.route,
        Screen.Settings.route
    )
    
    val shouldShowBottomBar = currentRoute in bottomBarRoutes
    
    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                AnimatedBottomBar(
                    currentRoute = currentRoute,
                    isArabic = isArabic,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues),
            isArabic = isArabic,
            isDarkMode = isDarkMode,
            favoriteIds = favoriteIds,
            onOnboardingComplete = onOnboardingComplete,
            onLanguageSelected = onLanguageSelected,
            onLanguageChange = onLanguageChange,
            onThemeChange = onThemeChange,
            onToggleFavorite = { figureId ->
                scope.launch {
                    favoritesDataStore?.toggleFavorite(figureId)
                }
            }
        )
    }
}
