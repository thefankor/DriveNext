package ru.fnkr.drivenextapp.data.auth

import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.delay
import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.data.supabase.SupabaseProvider
import ru.fnkr.drivenextapp.domain.model.User
import ru.fnkr.drivenextapp.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {

    private val supabase = SupabaseProvider.client

    override suspend fun signIn(email: String, password: String): AppResult<User> {
        return try {
            supabase.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            val userInfo: UserInfo? = supabase.auth.currentUserOrNull()

            if (userInfo != null) {
                AppResult.Ok(User(id = userInfo.id, email = userInfo.email ?: email))
            } else {
                AppResult.Err("Неверная почта или пароль")
            }
        } catch (e: Exception) {
            AppResult.Err(e.message ?: "Ошибка подключения к серверу")
        }
    }

    override suspend fun signUp(email: String, password: String): AppResult<User> {
        return try {
            supabase.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }

            val user = supabase.auth.currentUserOrNull()

            if (user != null) {
                AppResult.Ok(User(id = user.id, email = user.email ?: email))
            } else {
                AppResult.Err("Ошибка регистрации: не удалось получить пользователя")
            }

        } catch (e: Exception) {
            AppResult.Err(e.message ?: "Ошибка регистрации")
        }
    }

    override suspend fun restoreSession(): AppResult<User?> {
        return try {
            val session = supabase.auth.currentSessionOrNull()
            if (session == null) {
                AppResult.Ok(null)
            } else {
                runCatching { supabase.auth.refreshCurrentSession() }
                val user = supabase.auth.currentUserOrNull()
                AppResult.Ok(user?.let { User(it.id, it.email) })
            }
        } catch (e: Exception) {
            AppResult.Err(e.message ?: "Ошибка восстановления сессии")
        }
    }

    override suspend fun signOut(): AppResult<Unit> {
        return try {
            supabase.auth.signOut()
            AppResult.Ok(Unit)
        } catch (e: Exception) {
            AppResult.Err(e.message ?: "Ошибка выхода")
        }
    }

    override suspend fun checkUserExists(email: String): AppResult<User> {
//        delay(500)
        return AppResult.Ok(User("1", email)) // пока заглушка
    }
}
