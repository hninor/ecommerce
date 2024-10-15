package com.example.marketplacepuj

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MarketPlacePUJApplication : Application() {

    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
        Log.d("MarketPlacePUJ", "MarketPlacePUJApplication onCreate")
    }
}