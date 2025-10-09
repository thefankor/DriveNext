package ru.fnkr.drivenextapp

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ru.fnkr.drivenextapp.databinding.OnboardingBinding
import androidx.core.content.edit

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: OnboardingBinding

    private var index = 0

    private val images = arrayOf(
        R.drawable.ic_onboarding_1,
        R.drawable.ic_onboarding_2,
        R.drawable.ic_onboarding_3
    )

    private val titles = arrayOf(
        R.string.onb1_title,
        R.string.onb2_title,
        R.string.onb3_title
    )

    private val taglines = arrayOf(
        R.string.obn1_description,
        R.string.obn2_description,
        R.string.obn3_description
    )

    private val dots by lazy {
        arrayOf(
            binding.btnStep1,
            binding.btnStep2,
            binding.btnStep3
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // теперь можно безопасно читать ресурсы
        val activeWidth = resources.getDimensionPixelSize(R.dimen.dot_active_width)
        val inactiveWidth = resources.getDimensionPixelSize(R.dimen.dot_inactive_width)

        index = savedInstanceState?.getInt(STATE_INDEX) ?: 0

        fun bindUi() {
            binding.imgOnboarding.setImageResource(images[index])
            binding.tvTitle.setText(titles[index])
            binding.tvDescription.setText(taglines[index])

            binding.btnContinue.setText(
                if (index == images.lastIndex) R.string.go else R.string.next
            )

            dots.forEachIndexed { i, btn ->
                val params = btn.layoutParams as ViewGroup.LayoutParams
                params.width = if (i == index) activeWidth else inactiveWidth
                val colorRes = if (i == index) R.color.purple else R.color.light_grey
                btn.backgroundTintList = ContextCompat.getColorStateList(this, colorRes)
                btn.layoutParams = params
                btn.requestLayout()
            }
        }

        bindUi()

        binding.btnContinue.setOnClickListener {
            if (index < images.lastIndex) {
                index++
                bindUi()
            } else {
                goToStartActivity()
            }
        }

        binding.tvSkip.setOnClickListener { goToStartActivity() }

        dots.forEachIndexed { i, btn ->
            btn.setOnClickListener {
                index = i
                bindUi()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_INDEX, index)
        super.onSaveInstanceState(outState)
    }

    private fun markOnboardingCompleted() {
        getSharedPreferences("app_prefs", MODE_PRIVATE)
            .edit {
                putBoolean("onboarding_completed", true)
            }
    }

    private fun goToStartActivity() {
        markOnboardingCompleted()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    companion object {
        private const val STATE_INDEX = "onboarding_index"
    }
}
