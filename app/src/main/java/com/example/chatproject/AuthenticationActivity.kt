
package com.example.chatproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_authetication.*
import android.os.StrictMode


class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authetication)

        authentication_email.setOnClickListener(){

            //이메일 인증번호 전송(이메일 형식 맞는지 확인)

            //화면전환
            val intent = Intent(this, AuthenticationActivity2::class.java)
            startActivity(intent)
        }
    }
}