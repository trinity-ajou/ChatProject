package com.example.chatproject

import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register.setOnClickListener{
            val intent = Intent(this,EmailActivity::class.java)
            startActivity(intent)
        }
        password.setOnClickListener {
            val intent = Intent(this,FindActivity::class.java)
            startActivity(intent)
        }
    }
}
