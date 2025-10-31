package ru.fnkr.drivenextapp.domain.repository

import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.domain.model.Car

interface CarRepository {
    suspend fun getAll(): AppResult<ArrayList<Car>>
    suspend fun search(query: String): AppResult<ArrayList<Car>>
}
