package com.example.chatproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_chat_list.*
import java.util.*
import kotlin.collections.ArrayList

class GroupMatch_main : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    val room_list = ArrayList<ChatList_Data>()
    var room_id = Hashtable<String, String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_match_main)

        setTitle("그룹 매치")

        var database = FirebaseDatabase.getInstance()
        var myRef = database.getReference("Room")

        viewManager = LinearLayoutManager(this)
        viewAdapter = ChatAdapter(this, room_list)

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