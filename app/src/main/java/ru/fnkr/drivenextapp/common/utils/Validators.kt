package ru.fnkr.drivenextapp.common.utils

import android.util.Patterns

object Validators {
    fun email(v: String): String? =
        when {
            v.isBlank() -> "Это поле является обязательным"
            !Patterns.EMAIL_ADDRESS.matcher(v).matches() -> "Введите корректный адрес электронной почты."
            else -> null
        }

    fun password(v: String): String? =
        when {
            v.isBlank() -> "Это поле является обязательным"
            v.length < 8 -> "Минимум 8 символов"
            else -> null
        }

    fun passwordAgain(password1: String, password2: String): String? =
        when {
            password2.isBlank() -> "Повторите пароль"
            password1 != password2 -> "Пароли не совпадают"
            else -> null
        }

    fun required(v: String): String? =
        when {
            v.isBlank() -> "Это поле является обязательным"
            else -> null
        }

}
