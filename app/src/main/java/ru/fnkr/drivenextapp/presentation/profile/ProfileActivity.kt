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
import ru.fnkr.drivenextapp.MainActivity
import ru.fnkr.drivenextapp.R
import ru.fnkr.drivenextapp.databinding.ActivityUserProfileBinding
import ru.fnkr.drivenextapp.presentation.auth.login.LoginActivity
import ru.fnkr.drivenextapp.presentation.home.HomeActivity
import kotlin.getValue

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private val vm: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        val home = binding.bottomContainer.findViewById<ImageView>(R.id.home)
        val settings = binding.bottomContainer.findViewById<ImageView>(R.id.settings)

        settings.setImageResource(R.drawable.ic_menu_settings_fill)
        setContentView(binding.root)
        vm.get_user()

        home.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, HomeActivity::class.java))
        }

        settings.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, SettingsActivity::class.java))
        }

        binding.llLogout.setOnClickListener {
            vm.user_logout()
            startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
            finish()
        }

        binding.llPassword.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, ChangePasswordActivity::class.java))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.ui.collect { state ->
                    if (!state.authorized) {
                        startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
                        finish()
                        return@collect
                    }

//                    binding.tvUserID.text = state.id ?: "—"
                    val logo = state.logo
                    if (!logo.isNullOrBlank()) {
                        binding.ivLogo.setImageURI(Uri.parse(logo))
                    }

                    binding.tvEmail.text = state.email ?: "—"
                    binding.tvGoogleEmail.text = state.email ?: "—"
                    binding.tvName.text = state.firstName ?: "—"
                    var genderStr = state.gender ?: "—"
                    if (genderStr == "male") {
                        binding.tvGender.text = "Мужской"
                    } else {
                        binding.tvGender.text = "Женский"
                    }


                }
            }
        }
    }
}
