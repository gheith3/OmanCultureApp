package com.oman.culture.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.oman.culture.ui.screens.detail.DetailScreen
import com.oman.culture.ui.screens.favorites.FavoritesScreen
import com.oman.culture.ui.screens.home.HomeScreen
import com.oman.culture.ui.screens.onboarding.OnboardingScreen
import com.oman.culture.ui.screens.settings.SettingsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
    isArabic: Boolean = false,
    isDarkMode: Boolean = false,
    favoriteIds: Set<Int> = emptySet(),
    onOnboardingComplete: () -> Unit = {},
    onLanguageSelected: (String) -> Unit = {},
    onLanguageChange: (Boolean) -> Unit = {},
    onThemeChange: (Boolean) -> Unit = {},
    onToggleFavorite: (Int) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Onboarding Screen
        composable(
            route = Screen.Onboarding.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            OnboardingScreen(
                onComplete = {
                    onOnboardingComplete()
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                onLanguageSelected = onLanguageSelected
            )
        }

        // Home Screen
        composable(
            route = Screen.Home.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            HomeScreen(
                onFigureClick = { figureId ->
                    navController.navigate(Screen.Detail.createRoute(figureId))
                },
                onCategoryClick = { categoryId ->
                    navController.navigate(Screen.Category.createRoute(categoryId))
                },
                isArabic = isArabic,
                onLanguageChange = onLanguageChange,
                favoriteIds = favoriteIds,
                onToggleFavorite = onToggleFavorite
            )
        }

        // Category Screen
        composable(
            route = Screen.Category.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.StringType }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            // CategoryScreen - simplified for now
        }

        // Detail Screen
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("figureId") { type = NavType.IntType }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) { backStackEntry ->
            val figureId = backStackEntry.arguments?.getInt("figureId") ?: 0
            DetailScreen(
                figureId = figureId,
                isArabic = isArabic,
                isFavorite = figureId in favoriteIds,
                onBackClick = { navController.popBackStack() },
                onToggleFavorite = { onToggleFavorite(figureId) }
            )
        }

        // Favorites Screen
        composable(
            route = Screen.Favorites.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            FavoritesScreen(
                favoriteIds = favoriteIds,
                isArabic = isArabic,
                onFigureClick = { figureId ->
                    navController.navigate(Screen.Detail.createRoute(figureId))
                },
                onRemoveFavorite = onToggleFavorite,
                onExploreClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        // Settings Screen
        composable(
            route = Screen.Settings.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            SettingsScreen(
                isArabic = isArabic,
                isDarkMode = isDarkMode,
                onLanguageChange = onLanguageChange,
                onThemeChange = onThemeChange
            )
        }
    }
}
