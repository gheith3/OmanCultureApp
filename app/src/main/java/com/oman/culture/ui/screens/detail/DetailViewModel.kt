package com.oman.culture.ui.screens.detail

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

data class DetailUiState(
    val figure: Figure? = null,
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val selectedTab: Int = 0
)

class DetailViewModel(
    application: Application
) : AndroidViewModel(application) {
    
    private val repository = FiguresRepository(application.applicationContext)
    
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()
    
    fun loadFigure(figureId: Int) {
        viewModelScope.launch {
            val figure = repository.getFigureById(figureId)
            _uiState.update {
                it.copy(
                    figure = figure,
                    isLoading = false
                )
            }
        }
    }
    
    fun setFavoriteStatus(isFavorite: Boolean) {
        _uiState.update { it.copy(isFavorite = isFavorite) }
    }
    
    fun onTabSelected(tabIndex: Int) {
        _uiState.update { it.copy(selectedTab = tabIndex) }
    }
}
