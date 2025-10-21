import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.fnkr.drivenextapp.common.utils.AppResult
import ru.fnkr.drivenextapp.data.auth.AuthRepositoryImpl
import ru.fnkr.drivenextapp.presentation.loading.Route
import ru.fnkr.drivenextapp.presentation.loading.RouteType

class LoadingViewModel() : ViewModel() {
    private val repo = AuthRepositoryImpl()
    val route = MutableStateFlow<Route?>(null)

    fun decideRoute() {
        viewModelScope.launch {
            delay(500)
            val result = repo.restoreSession()
            println(result)
            when (result) {
                is AppResult.Ok -> {
                    val user = result.value
                    route.value = if (user != null) {
                        Route(RouteType.PROFILE, user.id, user.email)
                    } else {
                        Route(RouteType.LOGIN)
                    }
                }
                is AppResult.Err -> route.value = Route(RouteType.LOGIN)
            }
        }
    }
}

