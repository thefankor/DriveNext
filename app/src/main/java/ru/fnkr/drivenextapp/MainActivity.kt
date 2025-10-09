package ru.fnkr.drivenextapp

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import ru.fnkr.drivenextapp.databinding.ActivityGettingStartedBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGettingStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGettingStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this@MainActivity, SignUp1Activity::class.java)
            startActivity(intent)
        }

    }
}