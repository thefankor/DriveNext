package ru.fnkr.drivenextapp.domain.usecase

import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.domain.model.User
import ru.fnkr.drivenextapp.domain.repository.AuthRepository

class SignInUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): AppResult<User> =
        repo.signIn(email, password)
}


class SignUpCheckExistUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String): AppResult<User> =
        repo.checkUserExists(email)
}
