package com.oman.culture.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oman.culture.ui.components.EmptyFavoritesState
import com.oman.culture.ui.components.FigureCard
import com.oman.culture.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    favoriteIds: Set<Int>,
    isArabic: Boolean,
    onFigureClick: (Int) -> Unit,
    onRemoveFavorite: (Int) -> Unit,
    onExploreClick: () -> Unit,
    viewModel: FavoritesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(favoriteIds) {
        viewModel.loadFavorites(favoriteIds)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isArabic) "المفضلة" else "Favorites",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Primary)
                }
            }
            uiState.favorites.isEmpty() -> {
                EmptyFavoritesState(
                    isArabic = isArabic,
                    onExploreClick = onExploreClick,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    // Count badge
                    Text(
                        text = "♥ ${uiState.favorites.size} ${if (isArabic) "شخصيات محفوظة" else "saved figures"}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Primary,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            items = uiState.favorites,
                            key = { it.id }
                        ) { figure ->
                            FigureCard(
                                figure = figure,
                                isArabic = isArabic,
                                isFavorite = true,
                                onClick = { onFigureClick(figure.id) },
                                onFavoriteClick = { onRemoveFavorite(figure.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
