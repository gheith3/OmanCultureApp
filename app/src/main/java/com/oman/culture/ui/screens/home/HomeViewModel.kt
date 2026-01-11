package com.oman.culture.ui.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.oman.culture.data.model.Category
import com.oman.culture.data.model.Figure
import com.oman.culture.data.repository.FiguresRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val figures: List<Figure> = emptyList(),
    val filteredFigures: List<Figure> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category? = null,
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val favoriteIds: Set<Int> = emptySet(),
    val isArabic: Boolean = false
)

class HomeViewModel(
    application: Application
) : AndroidViewModel(application) {
    
    private val repository = FiguresRepository(application.applicationContext)
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            val figures = repository.getAllFigures()
            val categories = repository.getCategories()
            _uiState.update {
                it.copy(
                    figures = figures,
                    filteredFigures = figures,
                    categories = categories,
                    isLoading = false
                )
            }
        }
    }
    
    fun onSearchQueryChange(query: String) {
        _uiState.update { state ->
            val filtered = if (query.isBlank()) {
                applyFilters(state.figures, state.selectedCategory)
            } else {
                repository.searchFigures(query).let { searchResults ->
                    applyFilters(searchResults, state.selectedCategory)
                }
            }
            state.copy(
                searchQuery = query,
                filteredFigures = filtered
            )
        }
    }
    
    fun onCategorySelected(category: Category?) {
        _uiState.update { state ->
            val baseList = if (state.searchQuery.isBlank()) {
                state.figures
            } else {
                repository.searchFigures(state.searchQuery)
            }
            val filtered = applyFilters(baseList, category)
            state.copy(
                selectedCategory = category,
                filteredFigures = filtered
            )
        }
    }
    
    private fun applyFilters(figures: List<Figure>, category: Category?): List<Figure> {
        return if (category == null) {
            figures
        } else {
            figures.filter { it.category == category }
        }
    }
    
    fun toggleFavorite(figureId: Int) {
        _uiState.update { state ->
            val newFavorites = if (figureId in state.favoriteIds) {
                state.favoriteIds - figureId
            } else {
                state.favoriteIds + figureId
            }
            state.copy(favoriteIds = newFavorites)
        }
    }
    
    fun setFavoriteIds(ids: Set<Int>) {
        _uiState.update { it.copy(favoriteIds = ids) }
    }
    
    fun setLanguage(isArabic: Boolean) {
        _uiState.update { it.copy(isArabic = isArabic) }
    }
}
