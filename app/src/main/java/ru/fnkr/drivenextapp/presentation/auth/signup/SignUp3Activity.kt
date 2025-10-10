package ru.fnkr.drivenextapp.presentation.auth.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import ru.fnkr.drivenextapp.databinding.SignUp3Binding

class SignUp3Activity : AppCompatActivity() {
    private lateinit var binding: SignUp3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUp3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            val intent = Intent(this@SignUp3Activity, SignUpSuccessActivity::class.java)
            startActivity(intent)
        }

        binding.imgBack.setOnClickListener {
            val intent = Intent(this@SignUp3Activity, SignUp2Activity::class.java)
            startActivity(intent)
        }
    }
}