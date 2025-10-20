package ru.fnkr.drivenextapp.presentation.auth.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import ru.fnkr.drivenextapp.databinding.SignUpSuccessBinding
import ru.fnkr.drivenextapp.presentation.profile.ProfileActivity

class SignUpSuccessActivity : AppCompatActivity() {
    private lateinit var binding: SignUpSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUpSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            startActivity(Intent(this@SignUpSuccessActivity, ProfileActivity::class.java))
            finish()
        }
    }
}