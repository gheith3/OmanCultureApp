package com.oman.culture.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oman.culture.data.model.Category
import com.oman.culture.ui.components.AllCategoryChip
import com.oman.culture.ui.components.CategoryChip
import com.oman.culture.ui.components.EmptySearchState
import com.oman.culture.ui.components.FigureCard
import com.oman.culture.ui.components.LanguageSwitcher
import com.oman.culture.ui.components.SearchBar
import com.oman.culture.ui.theme.Primary
import com.oman.culture.ui.theme.Tertiary
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onFigureClick: (Int) -> Unit,
    onCategoryClick: (String) -> Unit,
    isArabic: Boolean,
    onLanguageChange: (Boolean) -> Unit,
    favoriteIds: Set<Int>,
    onToggleFavorite: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Gradient accent dot
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(Primary, Tertiary)
                                    ),
                                    shape = CircleShape
                                )
                        )
                        Text(
                            text = if (isArabic) "اكتشف عُمان" else "Discover Oman",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        )
                    }
                },
                actions = {
                    LanguageSwitcher(
                        isArabic = isArabic,
                        onLanguageChange = onLanguageChange,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            SearchBar(
                query = uiState.searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                isArabic = isArabic,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            // Category Chips with fade-in animation
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // "All" chip
                item {
                    AllCategoryChip(
                        isSelected = uiState.selectedCategory == null,
                        isArabic = isArabic,
                        onClick = { viewModel.onCategorySelected(null) }
                    )
                }
                
                items(uiState.categories) { category ->
                    CategoryChip(
                        category = category,
                        isSelected = uiState.selectedCategory == category,
                        isArabic = isArabic,
                        onClick = { viewModel.onCategorySelected(category) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Content
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Primary)
                    }
                }
                uiState.filteredFigures.isEmpty() -> {
                    EmptySearchState(isArabic = isArabic)
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        itemsIndexed(
                            items = uiState.filteredFigures,
                            key = { _, figure -> figure.id }
                        ) { index, figure ->
                            // Staggered animation for each card
                            var visible by remember { mutableStateOf(false) }
                            LaunchedEffect(figure.id) {
                                delay(index * 50L)
                                visible = true
                            }
                            
                            AnimatedVisibility(
                                visible = visible,
                                enter = fadeIn(
                                    animationSpec = tween(300)
                                ) + slideInVertically(
                                    initialOffsetY = { it / 2 },
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                ),
                                exit = fadeOut()
                            ) {
                                FigureCard(
                                    figure = figure,
                                    isArabic = isArabic,
                                    isFavorite = figure.id in favoriteIds,
                                    onClick = { onFigureClick(figure.id) },
                                    onFavoriteClick = { onToggleFavorite(figure.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
