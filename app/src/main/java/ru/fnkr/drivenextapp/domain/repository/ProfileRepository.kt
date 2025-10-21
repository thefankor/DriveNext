package ru.fnkr.drivenextapp.domain.repository

import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.domain.model.Profile
import ru.fnkr.drivenextapp.domain.model.ProfileShort
import ru.fnkr.drivenextapp.presentation.auth.common.SignUpData

interface ProfileRepository {
    suspend fun getMyProfile(email: String, password: String): AppResult<Profile?>
    suspend fun upsertMyProfile(p: Profile): AppResult<Unit>

    suspend fun getMyProfile(userId: String): AppResult<ProfileShort>
}
