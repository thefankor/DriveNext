package ru.fnkr.drivenextapp.presentation.auth.signup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import ru.fnkr.drivenextapp.databinding.SignUp3Binding
import ru.fnkr.drivenextapp.presentation.auth.common.SignUpData
import kotlin.getValue

class SignUp3Activity : AppCompatActivity() {
    private lateinit var binding: SignUp3Binding

    private val vm: SignUpViewModel by viewModels()

    private var data: SignUpData = SignUpData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUp3Binding.inflate(layoutInflater)
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

        binding.btnContinue.setOnClickListener {
            val i = Intent(this@SignUp3Activity, SignUpSuccessActivity::class.java)
                .putExtra(EXTRA_SIGN_UP_DATA, data)
            startActivity(i)
        }

        binding.imgBack.setOnClickListener {
            val i = Intent(this@SignUp3Activity, SignUp2Activity::class.java)
                .putExtra(EXTRA_SIGN_UP_DATA, data)
            startActivity(i)
        }
    }
}