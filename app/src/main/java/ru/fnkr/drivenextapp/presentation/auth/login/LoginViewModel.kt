package ru.fnkr.drivenextapp.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.data.auth.AuthRepositoryImpl
import ru.fnkr.drivenextapp.domain.usecase.SignInUseCase
import ru.fnkr.drivenextapp.domain.usecase.ValidateLoginFields
import ru.fnkr.drivenextapp.presentation.auth.AuthUiState

class LoginViewModel : ViewModel() {

    private val validate = ValidateLoginFields()
    private val signIn = SignInUseCase()

    private val _ui = MutableStateFlow(AuthUiState())
    val ui: StateFlow<AuthUiState> = _ui

    fun submit(email: String, password: String) {
        val result = validate(email, password)
        if (!result.valid) {
            _ui.update {
                it.copy(
                    emailError = result.emailError,
                    passError = result.passError
                )
            }
            return
        }

        viewModelScope.launch {
            _ui.update { it.copy(isLoading = true, generalError = null) }

            when (val res = signIn(email, password)) {
                is AppResult.Ok ->
                    _ui.update { it.copy(isLoading = false, isAuthorized = true) }

                is AppResult.Err ->
                    _ui.update { it.copy(isLoading = false, generalError = res.message) }
            }
        }
    }
}
