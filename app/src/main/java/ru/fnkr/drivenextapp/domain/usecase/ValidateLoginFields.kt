package ru.fnkr.drivenextapp.domain.usecase

import ru.fnkr.drivenextapp.common.utils.Validators

data class LoginValidationResult(
    val valid: Boolean,
    val emailError: String? = null,
    val passError: String? = null
)


data class ChangePasswordValidationResult(
    val valid: Boolean,
    val pass1Error: String? = null,
    val pass2Error: String? = null,
)


class ValidateLoginFields {
    operator fun invoke(email: String, pass: String): LoginValidationResult {
        val e = Validators.email(email)
        val p = Validators.password(pass)
        return LoginValidationResult(
            valid = e == null && p == null,
            emailError = e,
            passError = p
        )
    }
}


class ValidatePasswordFields {
    operator fun invoke(password1: String, password2: String): ChangePasswordValidationResult {
        val p1 = Validators.password(password1)
        val p2 = Validators.password(password2)
        val pAgain = Validators.passwordAgain(password1, password2)
        val pass2ErrToShow = p2 ?: pAgain

        return ChangePasswordValidationResult(
            valid = p1 == null && pass2ErrToShow == null,
            pass1Error = p1,
            pass2Error = pass2ErrToShow,
        )
    }
}
