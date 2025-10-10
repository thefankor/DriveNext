package ru.fnkr.drivenextapp.presentation.auth

data class AuthUiState(
    val emailError: String? = null,
    val passError: String? = null,
    val generalError: String? = null,
    val isLoading: Boolean = false,
    val isAuthorized: Boolean = false
)

data class SignUpUiState(
    val emailError: String? = null,
    val pass1Error: String? = null,
    val pass2Error: String? = null,
    val policyError: String? = null,
    val generalError: String? = null,
    val isLoading: Boolean = false,
    val isAuthorized: Boolean = false
)

data class SignUpSecondUiState(
    val lastNameError: String? = null,
    val firstNameError: String? = null,
    val middleNameError: String? = null,
    val birthDayError: String? = null,
    val generalError: String? = null,
    val isLoading: Boolean = false,
    val isAuthorized: Boolean = false
)