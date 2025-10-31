package ru.fnkr.drivenextapp.presentation.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.os.Handler

import androidx.appcompat.app.AppCompatActivity

import ru.fnkr.drivenextapp.databinding.ActivitySearchLoadingBinding

class SearchLoadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchQuery: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getStringExtra(EXTRA_SEARCH_QUERY) ?: ""
        } else {
            @Suppress("DEPRECATION")
            intent.getStringExtra(EXTRA_SEARCH_QUERY) ?: ""
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this@SearchLoadingActivity, SearchActivity::class.java)
                .putExtra(EXTRA_SEARCH_QUERY, searchQuery)
            startActivity(i)
            finish()
        }, 1000)
    }
}