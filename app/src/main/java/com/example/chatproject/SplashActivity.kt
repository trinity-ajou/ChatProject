package com.example.chatproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(1000)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}