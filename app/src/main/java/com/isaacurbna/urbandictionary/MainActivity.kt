package com.isaacurbna.urbandictionary

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.isaacurbna.urbandictionary.retrofit.interfaces.OnlineApi
import com.isaacurbna.urbandictionary.room.TermsDao
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    @Inject
    lateinit var termsDao: TermsDao
    @Inject
    lateinit var onlineApi: OnlineApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UrbanDictionaryApp
            .applicationComponent
            .inject(this)
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "termsDao: $termsDao")
        Log.i(TAG, "onlineApi: $onlineApi")
    }
}
