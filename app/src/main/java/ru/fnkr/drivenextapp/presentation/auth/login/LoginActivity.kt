package ru.fnkr.drivenextapp.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.fnkr.drivenextapp.MainActivity
import ru.fnkr.drivenextapp.common.utils.launchNoConnectionIfNeeded
import ru.fnkr.drivenextapp.presentation.auth.signup.SignUp1Activity
import ru.fnkr.drivenextapp.databinding.ActivityLoginBinding
import ru.fnkr.drivenextapp.presentation.profile.ProfileActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val vm: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            launchNoConnectionIfNeeded()
            val email = binding.ilEditEmail.text?.toString().orEmpty()
            val pass  = binding.ilEditPassword.text?.toString().orEmpty()
            vm.submit(email, pass)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.ui.collect { s ->
                    binding.ilEmail.error = s.emailError

                    binding.ilPassword.error = s.passError

                    binding.btnLogin.isEnabled = !s.isLoading

                    if (s.generalError != null) {
                        binding.ilEmail.error = null
                        binding.ilPassword.error = null
                        Snackbar.make(binding.root, s.generalError, Snackbar.LENGTH_SHORT).show()
                    }

                    if (s.isAuthorized) {
                        startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
                        finish()
                    }
                }
            }
        }


        binding.btnGoogleOAuth.setOnClickListener {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUp1Activity::class.java)
            startActivity(intent)
        }

    }
}