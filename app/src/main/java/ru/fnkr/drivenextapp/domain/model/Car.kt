package ru.fnkr.drivenextapp.domain.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Car(
    val brand: String,
    val model: String,
    val price: Int,
    val gearbox: String,
    val fuel: String,
    @SerialName("img_url") val imgUrl: String? = null,
)
