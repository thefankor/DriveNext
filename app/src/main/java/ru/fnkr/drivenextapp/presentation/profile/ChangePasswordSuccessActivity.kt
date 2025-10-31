package ru.fnkr.drivenextapp.presentation.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.fnkr.drivenextapp.databinding.ChangePasswordSuccessBinding

class ChangePasswordSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ChangePasswordSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChangePasswordSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            startActivity(Intent(this@ChangePasswordSuccessActivity, ProfileActivity::class.java))
        }
    }
}
