package ru.fnkr.drivenextapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import ru.fnkr.drivenextapp.databinding.SignUp2Binding

class SignUp2Activity : AppCompatActivity() {
    private lateinit var binding: SignUp2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUp2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            val intent = Intent(this@SignUp2Activity, SignUp3Activity::class.java)
            startActivity(intent)
        }

        binding.imgBack.setOnClickListener {
            val intent =  Intent(this@SignUp2Activity, SignUp1Activity::class.java)
            startActivity(intent)
        }
    }
}