package ru.fnkr.drivenextapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import ru.fnkr.drivenextapp.databinding.SignUp1Binding

class SignUp1Activity : AppCompatActivity() {
    private lateinit var binding: SignUp1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUp1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            val intent = Intent(this@SignUp1Activity, SignUp2Activity::class.java)
            startActivity(intent)
        }

        binding.imgBack.setOnClickListener {
            val intent =  Intent(this@SignUp1Activity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
