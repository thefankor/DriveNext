package ru.fnkr.drivenextapp.domain.usecase

import ru.fnkr.drivenextapp.common.utils.Validators

data class LoginValidationResult(
    val valid: Boolean,
    val emailError: String? = null,
    val passError: String? = null
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
