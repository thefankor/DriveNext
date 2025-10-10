package ru.fnkr.drivenextapp.presentation.auth.signup

import android.content.Intent
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
import kotlin.getValue

class SignUp1Activity : AppCompatActivity() {
    private lateinit var binding: SignUp1Binding
    private val vm: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUp1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            val email = binding.ilEditEmail.text?.toString().orEmpty()
            val pass1  = binding.ilEditPassword.text?.toString().orEmpty()
            val pass2  = binding.ilEditPasswordRepeat.text?.toString().orEmpty()
            val policyChecked = binding.cbPolicy.isChecked
            vm.submitFirst(email=email, password1=pass1, password2=pass2, policyChecked=policyChecked)
//            val intent = Intent(this@SignUp1Activity, SignUp2Activity::class.java)
//            startActivity(intent)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.ui.collect { s ->
                    binding.ilEmail.error = s.emailError

                    binding.ilPassword.error = s.pass1Error
                    binding.ilPasswordRepeat.error = s.pass2Error
                    print(s.policyError)
                    binding.tvPolicyError.isVisible = s.policyError != null

                    binding.btnContinue.isEnabled = !s.isLoading

                    if (s.generalError != null) {
                        binding.ilEmail.error = null
                        binding.ilPassword.error = null
                        binding.ilPasswordRepeat.error = null
                        binding.tvPolicyError.isVisible = false
                        Snackbar.make(binding.root, s.generalError, Snackbar.LENGTH_SHORT).show()
                    }

                    if (s.isAuthorized) {
                        val intent = Intent(this@SignUp1Activity, SignUp2Activity::class.java)
//                        intent.putExtra("signup_data", data)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

        binding.cbPolicy.setOnCheckedChangeListener { _, isChecked ->
            binding.tvPolicyError.isVisible = !isChecked
        }

        binding.imgBack.setOnClickListener {
            val intent =  Intent(this@SignUp1Activity, MainActivity::class.java)
//            intent.putExtra("signup_data", data)
            startActivity(intent)
        }
    }
}
