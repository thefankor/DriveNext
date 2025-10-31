package ru.fnkr.drivenextapp.domain.model

data class Car(
    val brand: String,
    val model: String,
    val price: Int,
    val gearbox: String,
    val fuel: String,
    val imgUrl: String,
)
