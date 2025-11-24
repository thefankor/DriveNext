package ru.fnkr.drivenextapp.presentation.profile

data class PasswordUiState(
    val oldPassError: String? = null,
    val pass1Error: String? = null,
    val pass2Error: String? = null,
    val generalError: String? = null,
    val isLoading: Boolean = false,
    val isAuthorized: Boolean = false
)
