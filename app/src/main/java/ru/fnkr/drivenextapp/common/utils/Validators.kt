package ru.fnkr.drivenextapp.common.utils

import android.util.Patterns

object Validators {
    fun email(v: String): String? =
        when {
            v.isBlank() -> "Это поле является обязательным"
            !Patterns.EMAIL_ADDRESS.matcher(v).matches() -> "Некорректный email"
            else -> null
        }

    fun password(v: String): String? =
        when {
            v.isBlank() -> "Это поле является обязательным"
            v.length < 8 -> "Минимум 8 символов"
            else -> null
        }
}
