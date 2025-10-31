package ru.fnkr.drivenextapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.data.home.CarRepositoryImpl
import ru.fnkr.drivenextapp.domain.model.Car

data class CarsUiState(
    val isLoading: Boolean = false,
    val items: List<Car> = emptyList(),
    val error: String? = null
)

class HomeViewModel : ViewModel() {
    private val repo = CarRepositoryImpl()

    private val _ui = MutableStateFlow(CarsUiState(isLoading = true))
    val ui: StateFlow<CarsUiState> = _ui

    init { load_all() }

    fun load_all() {
        _ui.value = _ui.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            when (val res = repo.getAll()) {
                is AppResult.Ok -> _ui.value = CarsUiState(
                    isLoading = false,
                    items = res.value
                )
                is AppResult.Err -> _ui.value = CarsUiState(
                    isLoading = false,
                    items = emptyList(),
                    error = res.message
                )
            }
        }
    }

    fun search(query: String) {
        _ui.value = _ui.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            when (val res = repo.search(query)) {
                is AppResult.Ok -> _ui.value = CarsUiState(
                    isLoading = false,
                    items = res.value
                )
                is AppResult.Err -> _ui.value = CarsUiState(
                    isLoading = false,
                    items = emptyList(),
                    error = res.message
                )
            }
        }
    }
}
