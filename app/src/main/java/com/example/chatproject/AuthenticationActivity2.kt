
package com.example.chatproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_authentication2.*
import android.os.StrictMode

class AuthenticationActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication2)

        emailAuth_btn.setOnClickListener(){

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}
