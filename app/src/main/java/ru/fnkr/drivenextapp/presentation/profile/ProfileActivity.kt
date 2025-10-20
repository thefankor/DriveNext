package ru.fnkr.drivenextapp.presentation.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.fnkr.drivenextapp.MainActivity
import ru.fnkr.drivenextapp.databinding.ActivityProfileBinding
import ru.fnkr.drivenextapp.presentation.auth.login.LoginActivity
import kotlin.getValue

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val vm: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm.get_user()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.ui.collect { state ->
                    if (!state.authorized) {
                        startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
                        finish()
                        return@collect
                    }

                    binding.tvUserID.text = state.id ?: "—"
                    binding.tvEmail.text = state.email ?: "—"

                }
            }
        }

        binding.btnLogout.setOnClickListener {
            vm.user_logout()
            startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
            finish()
        }
    }
}
