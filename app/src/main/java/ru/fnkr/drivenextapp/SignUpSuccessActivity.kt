package ru.fnkr.drivenextapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import ru.fnkr.drivenextapp.databinding.SignUpSuccessBinding
import ru.fnkr.drivenextapp.common.utils.launchNoConnectionIfNeeded

class SignUpSuccessActivity : AppCompatActivity() {
    private lateinit var binding: SignUpSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUpSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
//            val intent = Intent(this@SignUpSuccessActivity, MainActivity::class.java)
//            startActivity(intent)
            launchNoConnectionIfNeeded()
        }
    }
}