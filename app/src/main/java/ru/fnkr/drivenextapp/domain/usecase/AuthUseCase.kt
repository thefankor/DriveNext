package ru.fnkr.drivenextapp.domain.usecase

import kotlinx.datetime.format
import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.data.auth.AuthRepositoryImpl
import ru.fnkr.drivenextapp.data.auth.ProfileRepositoryImpl
import ru.fnkr.drivenextapp.domain.model.Profile
import ru.fnkr.drivenextapp.domain.model.User
import ru.fnkr.drivenextapp.presentation.auth.common.SignUpData
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.String

class SignInUseCase() {
    private val repo = AuthRepositoryImpl()
    suspend operator fun invoke(email: String, password: String): AppResult<User> =
        repo.signIn(email, password)
}


class SignUpUseCase() {
    private val authRepo = AuthRepositoryImpl()
    private val profileRepo = ProfileRepositoryImpl()
    suspend operator fun invoke(email: String, password: String, authData: SignUpData): AppResult<User> {

        val signUp = authRepo.signUp(email, password)
        return when (signUp) {
            is AppResult.Err -> signUp
            is AppResult.Ok -> {
                val user = signUp.value

                val profile = Profile(
                    id=user.id,
                    firstName=authData.firstName.orEmpty(),
                    lastName=authData.lastName.orEmpty(),
                    middleName=authData.middleName.orEmpty(),
                    birthDay=transform_date(authData.birthDate.orEmpty()).orEmpty(),
                    gender=authData.sex.orEmpty(),
                    licenseNumber=authData.licenseNumber.orEmpty(),
                    licenseDate=transform_date(authData.licenseDate.orEmpty()).orEmpty(),
                    avatarURL=authData.profilePhotoUri.orEmpty(),
                    passportURL=authData.passportPhotoUri.orEmpty(),
                    licenseURL=authData.licensePhotoUri.orEmpty(),
                )
                println(profile)

                when (val upsert = profileRepo.upsertMyProfile(profile)) {
                    is AppResult.Ok -> AppResult.Ok(user)
                    is AppResult.Err -> AppResult.Err("Регистрация выполнена, но профиль не сохранился: ${upsert.message}")
                }
            }
        }
    }

    fun transform_date(date: String): String? {
        val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val outputFormatter = DateTimeFormatter.ISO_DATE
        return runCatching {
            LocalDate.parse(date, inputFormatter).format(outputFormatter)
        }.getOrNull()
    }
}


class SignUpCheckExistUseCase() {
    private val repo = AuthRepositoryImpl()
    suspend operator fun invoke(email: String): AppResult<User> =
        repo.checkUserExists(email)
}
