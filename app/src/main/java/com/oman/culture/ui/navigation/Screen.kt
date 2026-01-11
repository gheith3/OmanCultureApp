package com.oman.culture.ui.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Category : Screen("category/{categoryId}") {
        fun createRoute(categoryId: String) = "category/$categoryId"
    }
    object Detail : Screen("detail/{figureId}") {
        fun createRoute(figureId: Int) = "detail/$figureId"
    }
    object Favorites : Screen("favorites")
    object Settings : Screen("settings")
}
