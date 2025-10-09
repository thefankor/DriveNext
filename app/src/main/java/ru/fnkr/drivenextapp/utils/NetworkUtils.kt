package ru.fnkr.drivenextapp.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import ru.fnkr.drivenextapp.NoConnectionActivity

fun Context.launchNoConnectionIfNeeded(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false.also {
        startActivity(Intent(this, NoConnectionActivity::class.java))
    }
    val caps = cm.getNetworkCapabilities(network) ?: return false.also {
        startActivity(Intent(this, NoConnectionActivity::class.java))
    }
    val ok = caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

    if (!ok) startActivity(Intent(this, NoConnectionActivity::class.java))
    return !ok
}
