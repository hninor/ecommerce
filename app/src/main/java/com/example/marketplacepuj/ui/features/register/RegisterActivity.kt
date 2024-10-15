package com.example.marketplacepuj.ui.features.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marketplacepuj.R
import com.example.marketplacepuj.ui.features.register.ui.RegisterFragment

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RegisterFragment.newInstance())
                .commitNow()
        }
    }
}