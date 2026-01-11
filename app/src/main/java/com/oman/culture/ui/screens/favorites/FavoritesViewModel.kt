package com.oman.culture.ui.screens.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.oman.culture.data.model.Figure
import com.oman.culture.data.repository.FiguresRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FavoritesUiState(
    val favorites: List<Figure> = emptyList(),
    val isLoading: Boolean = true
)

class FavoritesViewModel(
    application: Application
) : AndroidViewModel(application) {
    
    private val repository = FiguresRepository(application.applicationContext)
    
    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()
    
    fun loadFavorites(favoriteIds: Set<Int>) {
        viewModelScope.launch {
            val favorites = repository.getFiguresByIds(favoriteIds.toList())
            _uiState.update {
                it.copy(
                    favorites = favorites,
                    isLoading = false
                )
            }
        }
    }
    
    fun updateFavorites(favoriteIds: Set<Int>) {
        viewModelScope.launch {
            val favorites = repository.getFiguresByIds(favoriteIds.toList())
            _uiState.update {
                it.copy(favorites = favorites)
            }
        }
    }
}
