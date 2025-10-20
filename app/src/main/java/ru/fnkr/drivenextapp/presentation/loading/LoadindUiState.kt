package ru.fnkr.drivenextapp.presentation.loading

enum class RouteType {
    LOGIN,
    PROFILE
}

data class Route(
    val type: RouteType,
    val userId: String? = null,
    val email: String? = null
)
