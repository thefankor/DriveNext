package ru.fnkr.drivenextapp.presentation.auth.common

import java.io.Serializable


data class SignUpData(
    val email: String? = null,
    val password: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val birthDate: String? = null,
) : Serializable
