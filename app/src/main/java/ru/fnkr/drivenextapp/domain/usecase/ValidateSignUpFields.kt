package ru.fnkr.drivenextapp.domain.usecase

import ru.fnkr.drivenextapp.common.utils.Validators

data class SignUpValidationResult(
    val valid: Boolean,
    val emailError: String? = null,
    val pass1Error: String? = null,
    val pass2Error: String? = null,
    val policyError: String? = null,
)

data class SignUpValidationSecondResult(
    val valid: Boolean,
    val lastNameError: String? = null,
    val firstNameError: String? = null,
    val middleNameError: String? = null,
    val birthDayError: String? = null,
)

data class SignUpValidationThirdResult(
    val valid: Boolean,
    val licenseNumberError: String? = null,
    val licenseDateError: String? = null,
)

class ValidateSignUpFields {
    operator fun invoke(email: String, password1: String, password2: String, policyChecked: Boolean): SignUpValidationResult {
        val e = Validators.email(email)
        val p1 = Validators.password(password1)
        val p2 = Validators.password(password2)
        val pAgain = Validators.passwordAgain(password1, password2)
        val policy = if (!policyChecked) "Необходимо согласие с политикой" else null

        val pass2ErrToShow = p2 ?: pAgain

        return SignUpValidationResult(
            valid = e == null && p1 == null && pass2ErrToShow == null && policy == null,
            emailError = e,
            pass1Error = p1,
            pass2Error = pass2ErrToShow,
            policyError = policy
        )
    }
}


class ValidateSignUpSecondFields {
    operator fun invoke(
        lastName: String,
        firstName: String,
        middleName: String,
        birthDay: String
    ): SignUpValidationSecondResult {
        val lastNameError = Validators.required(lastName)
        val firstNameError = Validators.required(firstName)
        val middleNameError = Validators.required(middleName)
        val birthDayError = Validators.required(birthDay)

        return SignUpValidationSecondResult(
            valid = lastNameError == null && firstNameError == null && middleNameError == null && birthDayError == null,
            lastNameError = lastNameError,
            firstNameError = firstNameError,
            middleNameError = middleNameError,
            birthDayError = birthDayError,
        )
    }
}

class ValidateSignUpThirdFields {
    operator fun invoke(licenseNumber: String, licenseDate: String): SignUpValidationThirdResult {
        val licenseNumberError = Validators.required(licenseNumber)
        val licenseDateError  = Validators.required(licenseDate)

        return SignUpValidationThirdResult(
            valid = licenseNumberError == null && licenseDateError == null,
            licenseNumberError = licenseNumberError,
            licenseDateError = licenseDateError,
        )
    }
}
