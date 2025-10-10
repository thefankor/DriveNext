package ru.fnkr.drivenextapp.presentation.auth.signup

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.fnkr.drivenextapp.R

import ru.fnkr.drivenextapp.databinding.SignUp2Binding
import ru.fnkr.drivenextapp.presentation.auth.common.SignUpData
import java.util.Calendar
import kotlin.getValue

class SignUp2Activity : AppCompatActivity() {
    private lateinit var binding: SignUp2Binding

    private val vm: SignUpViewModel by viewModels()

    private var data: SignUpData = SignUpData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUp2Binding.inflate(layoutInflater)
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

        binding.ilEditLastName.setText(data.lastName.orEmpty())
        binding.ilEditFirstName.setText(data.firstName.orEmpty())
        binding.ilEditMiddleName.setText(data.middleName.orEmpty())
        binding.ilEditBirthDay.setText(data.birthDate.orEmpty())

        binding.btnContinue.setOnClickListener {
            val lastName = binding.ilEditLastName.text?.toString().orEmpty()
            val firstName = binding.ilEditFirstName.text?.toString().orEmpty()
            val middleName = binding.ilEditMiddleName.text?.toString().orEmpty()
            val birthDate = binding.ilEditBirthDay.text?.toString().orEmpty()

            vm.submitSecond(lastName, firstName, middleName, birthDate)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.ui2.collect { s ->
                    binding.ilLastName.error = s.lastNameError
                    binding.ilFirstName.error = s.firstNameError
                    binding.ilMiddleName.error = s.middleNameError
                    binding.ilBirthDay.error = s.birthDayError

                    if (s.generalError != null)
                        Snackbar.make(binding.root, s.generalError, Snackbar.LENGTH_SHORT).show()

                    if (s.isAuthorized) {
                        val selectedId = binding.radioGroupSex.checkedRadioButtonId
                        val sex = when (selectedId) {
                            R.id.radioMan -> "male"
                            R.id.radioWoman -> "female"
                            else -> "male"
                        }

                        data = data.copy(
                            lastName = binding.ilEditLastName.text?.toString(),
                            firstName = binding.ilEditFirstName.text?.toString(),
                            middleName = binding.ilEditMiddleName.text?.toString(),
                            birthDate = binding.ilEditBirthDay.text?.toString(),
                            sex = sex,
                        )
                        val i = Intent(this@SignUp2Activity, SignUp3Activity::class.java)
                            .putExtra(EXTRA_SIGN_UP_DATA, data)
                        startActivity(i)
                    }
                }
            }
        }

        binding.imgBack.setOnClickListener {
            val i = Intent(this@SignUp2Activity, SignUp1Activity::class.java)
                .putExtra(EXTRA_SIGN_UP_DATA, data)
            startActivity(i)
        }

        binding.ilBirthDay.setStartIconOnClickListener {
            showDatePicker()
        }

        binding.ilEditBirthDay.setOnClickListener {
            showDatePicker()
        }

    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()

        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val formatted = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                binding.ilEditBirthDay.setText(formatted)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()
    }

}