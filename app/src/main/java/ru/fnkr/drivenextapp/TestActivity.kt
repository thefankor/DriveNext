package ru.fnkr.drivenextapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import ru.fnkr.drivenextapp.databinding.ActivityGettingStartedBinding
import ru.fnkr.drivenextapp.databinding.ActivityLoginBinding
import ru.fnkr.drivenextapp.databinding.NoConnectionBinding
import java.io.Serializable

class MainActivity1 : AppCompatActivity() {

//    private lateinit var binding: NoConnectionBinding

    private var _binding: ActivityGettingStartedBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for NoConnectionBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGettingStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.twLoginErrorMsg.isVisible = false
//        binding.twPassErrorMsg.isVisible = false

        val word = ExtraWord(
            "abc",
            "абц"
        )

        binding.btnSignIn.setOnClickListener {
//            binding.twLoginErrorMsg.isVisible = true
//            binding.twPassErrorMsg.isVisible = true
//            binding.llEmail.setPadding(0,0,0,0)
//            val intent = Intent(this@MainActivity, LoginActivity::class.java)
//            intent.putExtra("WORD", word)
//
//            val bundle = Bundle()
//            bundle.putSerializable("WORD", word)
//            intent.putExtras(bundle)
//            intent.putExtras(
//                bundleOf(
//                    "WORD" to word
//                )
//            )
//            startActivity(intent)
        }


//        binding.btnTryAgain.text = "AAAAAA"
//        binding.btnTryAgain.setTextColor(Color.GRAY)

//        with(binding) {
//            btnTryAgain.text = "AAAAAA"
//            btnTryAgain.setTextColor(Color.GRAY)
//        }
//        enableEdgeToEdge()

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
    data class ExtraWord(
        val original: String,
        val translate: String,
        val learned: Boolean = false,
    ): Serializable
}