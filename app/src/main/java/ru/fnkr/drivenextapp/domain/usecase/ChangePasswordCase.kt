package ru.fnkr.drivenextapp.domain.usecase

import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.data.auth.ProfileRepositoryImpl

class ChangePasswordCase {
    private val repo = ProfileRepositoryImpl()
    suspend operator fun invoke(oldPassword: String, newPassword: String): AppResult<Unit> =
        repo.changePassword(oldPassword, newPassword)
}
