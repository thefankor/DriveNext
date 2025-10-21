package ru.fnkr.drivenextapp.domain.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Profile(
    val id: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val birthDay: String,
    val gender: String,
    val licenseNumber: String,
    val licenseDate: String,
    val avatarURL: String,
    val passportURL: String,
    val licenseURL: String,
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ProfileShort(
    val id: String,
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    val gender: String? = null,
)
