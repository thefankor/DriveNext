package ru.fnkr.drivenextapp.presentation.auth.signup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.fnkr.drivenextapp.MainActivity
import ru.fnkr.drivenextapp.databinding.SignUp1Binding
import ru.fnkr.drivenextapp.presentation.auth.common.SignUpData

const val EXTRA_SIGN_UP_DATA = "signup_data"

class SignUp1Activity : AppCompatActivity() {

    private lateinit var binding: SignUp1Binding
    private val vm: SignUpViewModel by viewModels()

    private var data: SignUpData = SignUpData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUp1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState?.getSerializable(EXTRA_SIGN_UP_DATA, SignUpData::class.java)
                ?: intent.getSerializableExtra(EXTRA_SIGN_UP_DATA, SignUpData::class.java)
                ?: SignUpData()
        } else {
            @Suppress("DEPRECATION")
            savedInstanceState?.getSerializable(EXTRA_SIGN_UP_DATA) as? SignUpData
                ?: @Suppress("DEPRECATION")
                intent.getSerializableExtra(EXTRA_SIGN_UP_DATA) as? SignUpData
                ?: SignUpData()
        }

        binding.ilEditEmail.setText(data.email.orEmpty())
        binding.ilEditPassword.setText(data.password.orEmpty())
        binding.ilEditPasswordRepeat.setText(data.password.orEmpty())
        binding.cbPolicy.isChecked = data.policy


        binding.btnContinue.setOnClickListener {
            val email = binding.ilEditEmail.text?.toString().orEmpty()
            val pass1 = binding.ilEditPassword.text?.toString().orEmpty()
            val pass2 = binding.ilEditPasswordRepeat.text?.toString().orEmpty()
            val policyChecked = binding.cbPolicy.isChecked

            vm.submitFirst(email, pass1, pass2, policyChecked)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.ui1.collect { s ->
                    binding.ilEmail.error = s.emailError
                    binding.ilPassword.error = s.pass1Error
                    binding.ilPasswordRepeat.error = s.pass2Error
                    binding.tvPolicyError.isVisible = s.policyError != null
                    binding.btnContinue.isEnabled = !s.isLoading

                    if (s.generalError != null)
                        Snackbar.make(binding.root, s.generalError, Snackbar.LENGTH_SHORT).show()

                    if (s.isAuthorized) {
                        data = data.copy(
                            email = binding.ilEditEmail.text?.toString(),
                            password = binding.ilEditPassword.text?.toString(),
                            policy = true,
                        )
                        val i = Intent(this@SignUp1Activity, SignUp2Activity::class.java)
                            .putExtra(EXTRA_SIGN_UP_DATA, data)
                        startActivity(i)
                    }
                }
            }
        }

        binding.cbPolicy.setOnCheckedChangeListener { _, isChecked ->
            binding.tvPolicyError.isVisible = !isChecked
        }

        binding.imgBack.setOnClickListener {
            val i = Intent(this@SignUp1Activity, MainActivity::class.java)
                .putExtra(EXTRA_SIGN_UP_DATA, data)
            startActivity(i)
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(
            EXTRA_SIGN_UP_DATA,
            data.copy(
                email = binding.ilEditEmail.text?.toString(),
                password = binding.ilEditPassword.text?.toString()
            )
        )
        super.onSaveInstanceState(outState)
    }
}
