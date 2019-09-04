package com.example.chatproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_main_screen.*
import kotlinx.android.synthetic.main.fragment_chat_list.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class main_screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        random_match_button.setOnClickListener {
        }
        bottom_menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.matching -> {
                val fragmentA = MatchingFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_screen_frame, fragmentA)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.chat_room -> {
                val fragmentB = ChatListFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_screen_frame, fragmentB)
                    .commit()
                random_match_button.setVisibility(View.GONE)
                return@OnNavigationItemSelectedListener true
            }
            R.id.more -> {
                val fragmentC = MoreFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_screen_frame, fragmentC)
                    .commit()
                random_match_button.setVisibility(View.GONE)
                return@OnNavigationItemSelectedListener true
            }
            R.id.group -> {
                val fragmentD = GroupFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_screen_frame, fragmentD)
                    .commit()
                random_match_button.setVisibility(View.GONE)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}

class ChatListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    val room_list = ArrayList<ChatList_Data>()
    var room_id = Hashtable<String, String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.fragment_chat_list, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var database = FirebaseDatabase.getInstance()
        var myRef = database.getReference("Room")

        viewManager = LinearLayoutManager(activity)
        viewAdapter = ChatAdapter( context,room_list)

        recyclerView = view_chatlist.apply {
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

            myRef.addChildEventListener(
                object : ChildEventListener {

                    override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {

                        // A new comment has been added, add it to the displayed list
                        val m = dataSnapshot.getValue(ChatList_Data::class.java)

                        // [START_EXCLUDE]
                        // Update RecyclerView
                        if (m is ChatList_Data) {
                            room_list.add(m)
                            //view_chatlist.scrollToPosition(room_list.size - 1)
                            viewAdapter.notifyItemInserted(room_list.size - 1)
                            // [END_EXCLUDE]
                        }
                    }

                    override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {

                    }

                    override fun onChildRemoved(dataSnapshot: DataSnapshot) {

                    }

                    override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {

                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })

        }

    }
}
