package ru.fnkr.drivenextapp.presentation.auth.signup;


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.data.auth.AuthRepositoryImpl
import ru.fnkr.drivenextapp.domain.usecase.SignUpCheckExistUseCase
import ru.fnkr.drivenextapp.domain.usecase.ValidateSignUpFields
import ru.fnkr.drivenextapp.presentation.auth.SignUpUiState

class SignUpViewModel : ViewModel() {

    private val validate = ValidateSignUpFields()
    private val repo = AuthRepositoryImpl()
    private val checkEmail = SignUpCheckExistUseCase(repo)

    private val _ui = MutableStateFlow(SignUpUiState())
    val ui: StateFlow<SignUpUiState> = _ui

    fun submitFirst(email: String, password1: String, password2: String, policyChecked: Boolean) {
        val result = validate(email, password1, password2, policyChecked)
        if (!result.valid) {
            _ui.update {
                it.copy(
                    emailError = result.emailError,
                    pass1Error = result.pass1Error,
                    pass2Error = result.pass2Error,
                    policyError = result.policyError,
                )
            }
            return
        }

        viewModelScope.launch {
            _ui.update { it.copy(isLoading = true, generalError = null) }

            when (val res = checkEmail(email)) {
                is AppResult.Ok ->
                    _ui.update { it.copy(isLoading = false, isAuthorized = true) }

                is AppResult.Err ->
                    _ui.update { it.copy(isLoading = false, emailError = res.message) }
            }
        }
    }
}
