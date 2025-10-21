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
import ru.fnkr.drivenextapp.domain.usecase.SignUpUseCase
import ru.fnkr.drivenextapp.domain.usecase.ValidateSignUpFields
import ru.fnkr.drivenextapp.domain.usecase.ValidateSignUpSecondFields
import ru.fnkr.drivenextapp.domain.usecase.ValidateSignUpThirdFields
import ru.fnkr.drivenextapp.presentation.auth.SignUpSecondUiState
import ru.fnkr.drivenextapp.presentation.auth.SignUpThirdUiState
import ru.fnkr.drivenextapp.presentation.auth.SignUpUiState
import ru.fnkr.drivenextapp.presentation.auth.common.SignUpData

class SignUpViewModel : ViewModel() {

    private val validate = ValidateSignUpFields()
    private val validate_second = ValidateSignUpSecondFields()
    private val validate_third = ValidateSignUpThirdFields()

    private val signUp = SignUpUseCase()
    private val checkEmail = SignUpCheckExistUseCase()

    private val _ui1 = MutableStateFlow(SignUpUiState())
    private val _ui2 = MutableStateFlow(SignUpSecondUiState())
    private val _ui3 = MutableStateFlow(SignUpThirdUiState())
    val ui1: StateFlow<SignUpUiState> = _ui1
    val ui2: StateFlow<SignUpSecondUiState> = _ui2
    val ui3: StateFlow<SignUpThirdUiState> = _ui3

    fun submitFirst(email: String, password1: String, password2: String, policyChecked: Boolean) {
        val result = validate(email, password1, password2, policyChecked)
        if (!result.valid) {
            _ui1.update {
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
            _ui1.update { it.copy(isLoading = true, generalError = null) }

            when (val res = checkEmail(email)) {
                is AppResult.Ok ->
                    _ui1.update { it.copy(isLoading = false, isAuthorized = true) }

                is AppResult.Err ->
                    _ui1.update { it.copy(isLoading = false, emailError = res.message) }
            }
        }
    }

    fun submitSecond(lastName: String, firstName: String, middleName: String, birthDay: String) {
        val result = validate_second(lastName, firstName, middleName, birthDay)
        if (!result.valid) {
            _ui2.update {
                it.copy(
                    lastNameError = result.lastNameError,
                    firstNameError = result.firstNameError,
                    middleNameError = result.middleNameError,
                    birthDayError = result.birthDayError,
                )
            }
            return
        }

        viewModelScope.launch {
            _ui2.update { it.copy(isLoading = false, generalError = null, isAuthorized = true) }
        }
    }

    fun submitThird(licenseNumber: String, licenseDate: String, data: SignUpData) {
        val result = validate_third(licenseNumber, licenseDate)
        if (!result.valid) {
            _ui3.update {
                it.copy(
                    licenseNumberError = result.licenseNumberError,
                    licenseDateError = result.licenseDateError,
                )
            }
            return
        }

        viewModelScope.launch {

            when (val res = signUp(data.email.orEmpty(), data.password.orEmpty(), data)) {
                is AppResult.Ok ->
                    _ui3.update { it.copy(isLoading = false, isAuthorized = true) }

                is AppResult.Err ->
                    _ui3.update { it.copy(isLoading = false, generalError = res.message) }
            }
        }
    }
}
