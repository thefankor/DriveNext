package ru.fnkr.drivenextapp.presentation.profile

import android.content.Intent

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.fnkr.drivenextapp.R
import ru.fnkr.drivenextapp.common.utils.launchNoConnectionIfNeeded
import ru.fnkr.drivenextapp.databinding.ChangePasswordBinding
import ru.fnkr.drivenextapp.presentation.home.HomeActivity
import kotlin.getValue
import kotlin.jvm.java

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ChangePasswordBinding
    private val vm: ChangePasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChangePasswordBinding.inflate(layoutInflater)
        val home = binding.bottomContainer.findViewById<ImageView>(R.id.home)
        val settings = binding.bottomContainer.findViewById<ImageView>(R.id.settings)

        settings.setImageResource(R.drawable.ic_menu_settings_fill)
        setContentView(binding.root)

        home.setOnClickListener {
            startActivity(Intent(this@ChangePasswordActivity, HomeActivity::class.java))
        }

        settings.setOnClickListener {
            startActivity(Intent(this@ChangePasswordActivity, SettingsActivity::class.java))
        }

        binding.ivCL.setOnClickListener {
            startActivity(Intent(this@ChangePasswordActivity, ProfileActivity::class.java))
        }

        binding.btnSave.setOnClickListener {
            launchNoConnectionIfNeeded()
            val oldPassword = binding.ilEditOldPassword.text?.toString().orEmpty()
            val newPassword1  = binding.ilEditNewPassword.text?.toString().orEmpty()
            val newPassword2  = binding.ilEditPasswordRepeat.text?.toString().orEmpty()
            vm.submit(oldPassword, newPassword1, newPassword2)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.ui.collect { s ->
                    binding.ilOldPassword.error = s.oldPassError
                    binding.ilNewPassword.error = s.pass1Error
                    binding.ilPasswordRepeat.error = s.pass2Error

                    binding.btnSave.isEnabled = !s.isLoading

                    if (s.generalError != null) {
                        binding.ilOldPassword.error = null
                        binding.ilNewPassword.error = null
                        binding.ilPasswordRepeat.error = null
                        Snackbar.make(binding.root, s.generalError, Snackbar.LENGTH_SHORT).show()
                    }

                    if (s.isAuthorized) {
                        startActivity(Intent(this@ChangePasswordActivity,
                            ChangePasswordSuccessActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}
