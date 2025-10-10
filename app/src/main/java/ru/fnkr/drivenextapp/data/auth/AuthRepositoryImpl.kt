package ru.fnkr.drivenextapp.data.auth

import kotlinx.coroutines.delay
import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.domain.model.User
import ru.fnkr.drivenextapp.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    override suspend fun signIn(email: String, password: String): AppResult<User> {
        delay(500) // имитация сети
        return if (email == "demo@mail.com" && password == "password1234")
            AppResult.Ok(User("1", email))
        else AppResult.Err("Неверный email или пароль")
    }

    override suspend fun checkUserExists(email: String): AppResult<User> {
        delay(500)
        return if (email != "demo@mail.com")
            AppResult.Ok(User("1", email))
        else AppResult.Err("Пользователь с таким адресом электронной почты уже существует")
    }


}

