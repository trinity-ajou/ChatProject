package com.example.chatproject

import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private val TAG : String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var database = FirebaseDatabase.getInstance()
        var login_info= Hashtable<String, String>()
        mAuth = FirebaseAuth.getInstance();

        login_email.setText("ssoo2024@naver.com")
        login_pw.setText("sskim505")

        login.setOnClickListener {

            if (login_email.text.toString().length == 0 || login_pw.text.toString().length == 0){
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                mAuth.signInWithEmailAndPassword(login_email.text.toString(), login_pw.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = mAuth.currentUser
                            var myRef = database.getReference().child("user_login")
                            login_info.put("email",login_email.text.toString())
                            myRef.setValue(login_info)
                            val intent = Intent(this,main_screen::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

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
