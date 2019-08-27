package com.example.chatproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        bottom_menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.matching -> {
                val fragmentA = MatchingFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_screen_frame, fragmentA)
                    .commit()
                //random_match_button.setVisibility(View.VISIBLE)
                //group_match_button.setVisibility(View.VISIBLE)
                return@OnNavigationItemSelectedListener true
            }
            R.id.chat_room -> {
                val fragmentB = ChatListFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_screen_frame, fragmentB)
                    .commit()
                random_match_button.setVisibility(View.GONE)
                group_match_button.setVisibility(View.GONE)
                return@OnNavigationItemSelectedListener true
            }
            R.id.more -> {
                val fragmentC = MoreFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_screen_frame, fragmentC)
                    .commit()
                random_match_button.setVisibility(View.GONE)
                group_match_button.setVisibility(View.GONE)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}

class MatchingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.activity_main_screen, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}

class ChatListFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_chatlist, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}

class MoreFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_more, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}

