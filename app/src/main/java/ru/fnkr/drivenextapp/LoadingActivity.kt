package ru.fnkr.drivenextapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import ru.fnkr.drivenextapp.databinding.LoadingBinding

class LoadingActivity : AppCompatActivity() {

    private lateinit var binding: LoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val seen = getSharedPreferences("app_prefs", MODE_PRIVATE)
            .getBoolean("onboarding_completed", false)

        val nextClass = if (seen) {
            MainActivity::class.java
        } else {
            OnboardingActivity::class.java
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@LoadingActivity, nextClass))
            finish()
        }, 3000)
    }
}