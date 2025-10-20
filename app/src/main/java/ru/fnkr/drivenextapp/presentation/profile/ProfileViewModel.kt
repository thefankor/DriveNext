package ru.fnkr.drivenextapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.data.auth.AuthRepositoryImpl

data class ProfileUiState(
    val authorized: Boolean = true,
    val id: String? = null,
    val email: String? = null,
    val error: String? = null
)

class ProfileViewModel(): ViewModel() {
    private val repo = AuthRepositoryImpl()
    private val _ui = MutableStateFlow(ProfileUiState())
    val ui: StateFlow<ProfileUiState> = _ui

    fun get_user() {
        viewModelScope.launch {
            val result = repo.restoreSession()
            when (result) {
                is AppResult.Ok -> {
                    val user = result.value
                    println(user)
                    _ui.value = if (user != null) {
                        ProfileUiState(true, user.id, user.email)
                    } else {
                        ProfileUiState(false)
                    }
                }
                is AppResult.Err -> ProfileUiState(false)
            }
        }
    }

    fun user_logout() {
        viewModelScope.launch {
            repo.signOut()
        }
    }
}
