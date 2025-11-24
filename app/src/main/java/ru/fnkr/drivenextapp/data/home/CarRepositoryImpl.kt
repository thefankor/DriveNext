package ru.fnkr.drivenextapp.data.home

import android.util.Log
import io.github.jan.supabase.postgrest.from
import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.data.supabase.SupabaseProvider
import ru.fnkr.drivenextapp.domain.model.Car
import ru.fnkr.drivenextapp.domain.model.Profile
import ru.fnkr.drivenextapp.domain.repository.CarRepository

class CarRepositoryImpl: CarRepository {

    private val supabase = SupabaseProvider.client

    override suspend fun getAll(): AppResult<ArrayList<Car>> {
        return try {
            val result = supabase
                .from("cars")
                .select()
                .decodeList<Car>()
            AppResult.Ok(ArrayList(result))
        } catch (e: Exception) {
            AppResult.Err(e.message.orEmpty())
        }
    }

    override suspend fun search(query: String): AppResult<ArrayList<Car>> {
        return try {
            val pattern = "%$query%"

            val result = supabase
                .from("cars")
                .select {
                    filter {
                        or {
                            ilike("brand", pattern)
                            ilike("model", pattern)
                        }
                    }
                }
                .decodeList<Car>()

            AppResult.Ok(ArrayList(result))
        } catch (e: Exception) {
            AppResult.Err(e.message.orEmpty())
        }
    }
}
