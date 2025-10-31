package ru.fnkr.drivenextapp.presentation.profile

import android.content.Intent

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.fnkr.drivenextapp.R
import ru.fnkr.drivenextapp.databinding.ChangePasswordBinding
import ru.fnkr.drivenextapp.presentation.profile.ChangePasswordSuccessActivity
import ru.fnkr.drivenextapp.presentation.home.HomeActivity
import kotlin.getValue
import kotlin.jvm.java

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ChangePasswordBinding
    private val vm: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChangePasswordBinding.inflate(layoutInflater)
        val home = binding.bottomContainer.findViewById<ImageView>(R.id.home)
        val settings = binding.bottomContainer.findViewById<ImageView>(R.id.settings)

        settings.setImageResource(R.drawable.ic_menu_settings_fill)
        setContentView(binding.root)
        vm.get_user()

        home.setOnClickListener {
            startActivity(Intent(this@ChangePasswordActivity, HomeActivity::class.java))
        }

        settings.setOnClickListener {
            startActivity(Intent(this@ChangePasswordActivity, SettingsActivity::class.java))
        }

        binding.ivCL.setOnClickListener {
            startActivity(Intent(this@ChangePasswordActivity, ProfileActivity::class.java))
        }

        binding.btnSave.setOnClickListener {
            startActivity(Intent(this@ChangePasswordActivity, ChangePasswordSuccessActivity::class.java))
        }
    }
}
