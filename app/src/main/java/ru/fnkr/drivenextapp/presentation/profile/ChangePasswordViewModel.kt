package ru.fnkr.drivenextapp.presentation.profile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.domain.usecase.ChangePasswordCase

import ru.fnkr.drivenextapp.domain.usecase.ValidatePasswordFields


class ChangePasswordViewModel : ViewModel() {
    private val validate = ValidatePasswordFields()
    private val changePass = ChangePasswordCase()

    private val _ui = MutableStateFlow(PasswordUiState())
    val ui: StateFlow<PasswordUiState> = _ui

    fun submit(oldPassword: String, password1: String, password2: String) {
        val result = validate(password1, password2)
        if (!result.valid) {
            _ui.update {
                it.copy(
                    pass1Error = result.pass1Error,
                    pass2Error = result.pass2Error
                )
            }
            return
        }

        viewModelScope.launch {
            _ui.update { it.copy(isLoading = true, generalError = null) }

            when (val res = changePass(oldPassword, password1)) {
                is AppResult.Ok ->
                    _ui.update { it.copy(isLoading = false, isAuthorized = true) }

                is AppResult.Err ->
                    _ui.update { it.copy(isLoading = false, generalError = res.message) }
            }
        }
    }
}