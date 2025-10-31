package ru.fnkr.drivenextapp.data.home

import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.domain.model.Car
import ru.fnkr.drivenextapp.domain.repository.CarRepository

class CarRepositoryImpl: CarRepository {

    override suspend fun getAll(): AppResult<ArrayList<Car>> {
        val cars =  arrayListOf(
            Car(brand="BMW", model="M5"),
            Car(brand="Mercedes", model="Benz"),
            Car(brand="Mercedes", model="Benz"),
            Car(brand="Mercedes", model="Benz"),
            Car(brand="Mercedes", model="Benz"),
            Car(brand="Mercedes", model="Benz"),
            Car(brand="Mercedes", model="Benz"),
        )
        return AppResult.Ok(cars)
    }

    override suspend fun search(query: String): AppResult<ArrayList<Car>> {
        val cars =  arrayListOf(
            Car(brand="BMW", model="M5"),
            Car(brand="Mercedes", model="Benz"),
            Car(brand="Mercedes", model="Benz"),
        )
        return AppResult.Ok(cars)
    }
}
