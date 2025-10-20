package ru.fnkr.drivenextapp.presentation.loading

import LoadingViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.fnkr.drivenextapp.MainActivity
import ru.fnkr.drivenextapp.OnboardingActivity
import ru.fnkr.drivenextapp.databinding.LoadingBinding
import ru.fnkr.drivenextapp.presentation.profile.ProfileActivity
import kotlin.getValue

class LoadingActivity : AppCompatActivity() {

    private lateinit var binding: LoadingBinding
    private val vm: LoadingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm.decideRoute()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.route.collect { route ->
                    when (route?.type) {
                        RouteType.PROFILE -> {
                            startActivity(Intent(this@LoadingActivity, ProfileActivity::class.java))
                            finish()
                        }

                        RouteType.LOGIN -> {
                            if (!isOnboardingCompleted()) {
                                startActivity(Intent(this@LoadingActivity, OnboardingActivity::class.java))
                                finish()
                            } else {
                                startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
                                finish()
                            }
                        }

                        null -> Unit
                    }
                }
            }
        }
    }

    private fun isOnboardingCompleted(): Boolean =
        getSharedPreferences("app_prefs", MODE_PRIVATE)
            .getBoolean("onboarding_completed", false)

}
