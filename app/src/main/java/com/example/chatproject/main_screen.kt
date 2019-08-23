package com.example.chatproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_screen.*

class main_screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        group_match_button.setOnClickListener {
            val groupmat_Intent = Intent(this, GroupMatch_main::class.java)
            startActivity(groupmat_Intent)
        }

        random_match_button.setOnClickListener {
            val match_Intent = Intent(this, ChatActivity::class.java)
            startActivity(match_Intent)
        }
    }
}