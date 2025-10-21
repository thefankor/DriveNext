package ru.fnkr.drivenextapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.data.auth.AuthRepositoryImpl
import ru.fnkr.drivenextapp.data.auth.ProfileRepositoryImpl
import kotlin.String

data class ProfileUiState(
    val authorized: Boolean = true,
    val id: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val gender: String? = null,
    val error: String? = null
)

class ProfileViewModel(): ViewModel() {
    private val userRepo = AuthRepositoryImpl()
    private val profileRepo = ProfileRepositoryImpl()
    private val _ui = MutableStateFlow(ProfileUiState())
    val ui: StateFlow<ProfileUiState> = _ui

    fun get_user() {
        viewModelScope.launch {
            val result = userRepo.restoreSession()
            when (result) {
                is AppResult.Ok -> {
                    val user = result.value
                    println(user)
                    if (user != null) {
                        val profile_res = profileRepo.getMyProfile(userId = user.id)
                        println(profile_res)
                        when (profile_res) {
                            is AppResult.Ok -> {
                                val profile = profile_res.value
                                println(profile)
                                _ui.value = ProfileUiState(
                                    authorized=true,
                                    id=user.id,
                                    email=user.email,
                                    firstName=profile.firstName,
                                    lastName=profile.lastName,
                                    gender=profile.gender,
                                )

                            }
                            is AppResult.Err -> _ui.value = ProfileUiState(false, error = profile_res.message)
                        }

                    } else {
                        _ui.value = ProfileUiState(false)
                    }
                }
                is AppResult.Err -> ProfileUiState(false)
            }
        }
    }

    fun user_logout() {
        viewModelScope.launch {
            userRepo.signOut()
        }
    }
}
