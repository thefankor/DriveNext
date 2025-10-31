package ru.fnkr.drivenextapp.presentation.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.fnkr.drivenextapp.MainActivity
import ru.fnkr.drivenextapp.R
import ru.fnkr.drivenextapp.databinding.ActivityHomeBinding
import ru.fnkr.drivenextapp.databinding.ActivitySearchBinding
import ru.fnkr.drivenextapp.presentation.auth.common.SignUpData
import ru.fnkr.drivenextapp.presentation.auth.signup.EXTRA_SIGN_UP_DATA
import ru.fnkr.drivenextapp.presentation.auth.signup.SignUp2Activity
import ru.fnkr.drivenextapp.presentation.profile.SettingsActivity

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val vm: HomeViewModel by viewModels()
    private val adapter = CarAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val home = binding.bottomContainer.findViewById<ImageView>(R.id.home)
        val settings = binding.bottomContainer.findViewById<ImageView>(R.id.settings)
        home.setImageResource(R.drawable.ic_menu_home_fill)

        setContentView(binding.root)

        binding.ivCL.setOnClickListener {
            val i = Intent(this@SearchActivity, HomeActivity::class.java)
            startActivity(i)
            finish()
        }

        home.setOnClickListener {
            startActivity(Intent(this@SearchActivity, HomeActivity::class.java))
        }

        settings.setOnClickListener {
            startActivity(Intent(this@SearchActivity, SettingsActivity::class.java))
        }

        val searchQuery: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getStringExtra(EXTRA_SEARCH_QUERY) ?: ""
        } else {
            @Suppress("DEPRECATION")
            intent.getStringExtra(EXTRA_SEARCH_QUERY) ?: ""
        }

        binding.rvCars.layoutManager = LinearLayoutManager(this@SearchActivity)
        binding.rvCars.adapter = adapter

        if (searchQuery.isNotEmpty()) vm.search(searchQuery)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.ui.collect { state ->
                    adapter.carList = ArrayList(state.items)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

}
