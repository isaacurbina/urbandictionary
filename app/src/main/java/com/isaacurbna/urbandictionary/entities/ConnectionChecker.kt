package com.isaacurbna.urbandictionary.entities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.isaacurbna.urbandictionary.model.interfaces.ConnectionManager

class ConnectionChecker(val context: Context) : ConnectionManager {

    @Suppress("DEPRECATION")
    override fun isOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

}