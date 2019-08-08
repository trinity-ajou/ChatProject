package com.example.chatproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_email.*
import android.os.StrictMode




class EmailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build()
        )

        imageView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        next.setOnClickListener{
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
        }
    }
}



