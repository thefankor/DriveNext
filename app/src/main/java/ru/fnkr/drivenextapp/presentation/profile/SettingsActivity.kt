package ru.fnkr.drivenextapp.presentation.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.fnkr.drivenextapp.R
import ru.fnkr.drivenextapp.databinding.ActivitySettingsBinding
import ru.fnkr.drivenextapp.presentation.auth.login.LoginActivity
import ru.fnkr.drivenextapp.presentation.home.HomeActivity
import kotlin.getValue

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val vm: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val home = binding.bottomContainer.findViewById<ImageView>(R.id.home)
        val settings = binding.bottomContainer.findViewById<ImageView>(R.id.settings)

        settings.setImageResource(R.drawable.ic_menu_settings_fill)
        setContentView(binding.root)
        vm.get_user()

        home.setOnClickListener {
            startActivity(Intent(this@SettingsActivity, HomeActivity::class.java))
        }

        binding.profile.setOnClickListener {
            startActivity(Intent(this@SettingsActivity, ProfileActivity::class.java))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.ui.collect { state ->
                    if (!state.authorized) {
                        startActivity(Intent(this@SettingsActivity, LoginActivity::class.java))
                        finish()
                        return@collect
                    }

                    val logo = state.logo
                    if (!logo.isNullOrBlank()) {
                        binding.ivUserSettings.setImageURI(Uri.parse(logo))
                    }

                    binding.tvEmail.text = state.email ?: "—"
                    binding.tvName.text = state.firstName ?: "—"


                }
            }
        }

    }
}
