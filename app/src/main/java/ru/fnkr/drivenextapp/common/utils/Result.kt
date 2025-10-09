package ru.fnkr.drivenextapp.common.utils

sealed interface AppResult<out T> {
    data class Ok<T>(val value: T): AppResult<T>
    data class Err(val message: String): AppResult<Nothing>
}
