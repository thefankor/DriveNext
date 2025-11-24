package ru.fnkr.drivenextapp.data.auth

import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.data.supabase.SupabaseProvider
import ru.fnkr.drivenextapp.domain.model.Profile
import ru.fnkr.drivenextapp.domain.model.ProfileShort
import ru.fnkr.drivenextapp.domain.repository.ProfileRepository


class ProfileRepositoryImpl : ProfileRepository {
    private val supabase = SupabaseProvider.client

    override suspend fun getMyProfile(email: String, password: String): AppResult<Profile?> {
        val uid = supabase.auth.currentUserOrNull()?.id ?: AppResult.Err("Ошибка: не удалось получить пользователя")
        val profile = supabase.from("profiles")
            .select {
                filter { eq("id", uid) }
                single()
            }
            .decodeAs<Profile>()
        return AppResult.Ok(profile)
    }

    override suspend fun upsertMyProfile(p: Profile): AppResult<Unit> {
        return try {
            supabase.from("profiles").update(
                mapOf(
                    "first_name" to p.firstName,
                    "last_name" to p.lastName,
                    "middle_name" to p.middleName,
                    "birthday" to p.birthDay,
                    "gender" to p.gender,
                    "license_number" to p.licenseNumber,
                    "license_date" to p.licenseDate,
                    "avatar_url" to p.avatarURL,
                    "passport_url" to p.passportURL,
                    "license_url" to p.licenseURL
                )
            ) {
                filter { eq("id", p.id) }
            }

            AppResult.Ok(Unit)

        } catch (e: Exception) {
            AppResult.Err(e.message ?: "Ошибка соединения с сервером")
        }
    }

    override suspend fun getMyProfile(userId: String): AppResult<ProfileShort> = try {
        val res = supabase
            .from("profiles")
            .select { filter { eq("id", userId) } }

        val list = res.decodeList<ProfileShort>()
        val profile = list.firstOrNull()
            ?: return AppResult.Err("Профиль не найден")

        AppResult.Ok(profile)
    } catch (e: Exception) {
        AppResult.Err(e.message ?: "Ошибка получения профиля")
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String): AppResult<Unit> {
        return try {
            val user = supabase.auth.currentUserOrNull()
            val email = user?.email

            if (email == null) {
                return AppResult.Err("Пользователь не авторизован")
            }

            runCatching {
                supabase.auth.signInWith(Email) {
                    this.email = email
                    this.password = oldPassword
                }
            }.onFailure {
                return AppResult.Err("Старый пароль неверный")
            }

            supabase.auth.updateUser {
                password = newPassword
            }

            AppResult.Ok(Unit)

        } catch (e: Exception) {
            AppResult.Err(e.message ?: "Ошибка смены пароля")
        }
    }

}