package ru.fnkr.drivenextapp.domain.repository

import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.domain.model.User

interface AuthRepository {
    suspend fun signIn(email: String, password: String): AppResult<User>
    suspend fun signUp(email: String, password: String): AppResult<User>
    suspend fun checkUserExists(email: String): AppResult<User>
    suspend fun restoreSession(): AppResult<User?>
    suspend fun signOut(): AppResult<Unit>
}
