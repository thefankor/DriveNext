package ru.fnkr.drivenextapp.presentation.auth

data class AuthUiState(
    val emailError: String? = null,
    val passError: String? = null,
    val generalError: String? = null,
    val isLoading: Boolean = false,
    val isAuthorized: Boolean = false
)