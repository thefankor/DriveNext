package ru.fnkr.drivenextapp.data.home

import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.domain.model.Car
import ru.fnkr.drivenextapp.domain.repository.CarRepository

class CarRepositoryImpl: CarRepository {

    override suspend fun getAll(): AppResult<ArrayList<Car>> {
        val cars =  arrayListOf(
            Car(brand="Mercedes-Benz", model="S 500 Sedan", price=5003, gearbox="A/T", fuel="Бензин", imgUrl=""),
            Car(brand="BMW", model="BMW M5 F90", price=10000, gearbox="A/T", fuel="Бензин", imgUrl="https://macote.fr/wp-content/uploads/2024/01/BMW-M5-F90-PNG-NO-BACKGROUND--1024x614.png"),
            Car(brand="BMW", model="BMW M5 G60", price=501, gearbox="A/T", fuel="Бензин", imgUrl="https://macote.fr/wp-content/uploads/2024/01/BMW-M5-G60-PNG-NO-BACKGROUND-e1705307890113.webp"),
            Car(brand="BMW", model="BMW M5 F10", price=502, gearbox="A/T", fuel="Бензин", imgUrl="https://macote.fr/wp-content/uploads/2024/01/BMW-M5-F10-BACKGROUND.png"),
            Car(brand="Mercedes-Benz", model="S 500 Sedan", price=5003, gearbox="A/T", fuel="Бензин", imgUrl=""),
            Car(brand="Mercedes-Benz", model="S 500 Sedan", price=50044, gearbox="A/T", fuel="Бензин", imgUrl=""),
            Car(brand="Mercedes-Benz", model="S 500 Sedan", price=50055, gearbox="A/T", fuel="Бензин", imgUrl=""),
            Car(brand="Mercedes-Benz", model="S 500 Sedan", price=50066, gearbox="A/T", fuel="Бензин", imgUrl=""),
            Car(brand="Mercedes-Benz", model="S 500 Sedan", price=700, gearbox="A/T", fuel="Бензин", imgUrl=""),
            Car(brand="Mercedes-Benz", model="S 500 Sedan", price=701, gearbox="A/T", fuel="Бензин", imgUrl=""),
            Car(brand="Mercedes-Benz", model="S 500 Sedan", price=702, gearbox="A/T", fuel="Бензин", imgUrl=""),
            Car(brand="Mercedes-Benz", model="S 500 Sedan", price=7000, gearbox="A/T", fuel="Бензин", imgUrl=""),
        )
        return AppResult.Ok(cars)
    }

    override suspend fun search(query: String): AppResult<ArrayList<Car>> {
        val cars =  arrayListOf(
            Car(brand="BMW", model="BMW M5 F90", price=10000, gearbox="A/T", fuel="Бензин", imgUrl="https://macote.fr/wp-content/uploads/2024/01/BMW-M5-F90-PNG-NO-BACKGROUND--1024x614.png"),
            Car(brand="BMW", model="BMW M5 G60", price=501, gearbox="A/T", fuel="Бензин", imgUrl="https://macote.fr/wp-content/uploads/2024/01/BMW-M5-G60-PNG-NO-BACKGROUND-e1705307890113.webp"),
            Car(brand="BMW", model="BMW M5 F10", price=502, gearbox="A/T", fuel="Бензин", imgUrl="https://macote.fr/wp-content/uploads/2024/01/BMW-M5-F10-BACKGROUND.png"),
        )
        return AppResult.Ok(cars)
    }
}
