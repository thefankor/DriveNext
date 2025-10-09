package ru.fnkr.drivenextapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ru.fnkr.drivenextapp.databinding.NoConnectionBinding
import ru.fnkr.drivenextapp.databinding.SignUp1Binding

class NoConnectionActivity : AppCompatActivity() {

    private lateinit var binding: NoConnectionBinding

    private lateinit var cm: ConnectivityManager
    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            // сеть появилась — уходим с экрана
            runOnUiThread {
                continueApp()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NoConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        binding.btnTryAgain.setOnClickListener {
                if (isOnline()) {
                    continueApp()
                } else {
                    Snackbar.make(it, "Интернет недоступен", Snackbar.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        // слушаем изменения сети
        cm.registerDefaultNetworkCallback(callback)
    }

    override fun onStop() {
        super.onStop()
        cm.unregisterNetworkCallback(callback)
    }

    private fun continueApp() {
        finish()
    }

    private fun isOnline(): Boolean {
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}
