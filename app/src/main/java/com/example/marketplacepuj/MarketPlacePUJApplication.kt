package com.example.marketplacepuj

import android.app.Application
import android.util.Log
import by.alexandr7035.banking.core.di.appModule
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.GlobalContext

class MarketPlacePUJApplication : Application() {

    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()

        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@MarketPlacePUJApplication)
            modules(appModule)
        }
        Log.d("MarketPlacePUJ", "MarketPlacePUJApplication onCreate")
    }
}