package com.example.chatproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chat.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    var email: String? = ""
    val comments = ArrayList<Msg>()
    var chat = Hashtable<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {

        var database = FirebaseDatabase.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var user = FirebaseAuth.getInstance().getCurrentUser()

        if (user != null) {
            email = user.getEmail()
        }

        btnSend.setOnClickListener() {
            var stText: String = etText.getText().toString();
            if (stText.equals("") || stText.isEmpty()) {
                Toast.makeText(this, "내용을 입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, stText, Toast.LENGTH_SHORT).show()

                var c = Calendar.getInstance();
                var df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                var formattedDate = df.format(c.getTime())


                var myRef = database.getReference().child(formattedDate)

                chat.put("email", email)
                chat.put("text", stText)
                myRef.setValue(chat)
            }
        }


        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(comments,email)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
            var myRef2 = database.getReference()
            myRef2.addChildEventListener(
                object : ChildEventListener {

                    override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {

                        // A new comment has been added, add it to the displayed list
                        val m = dataSnapshot.getValue(Msg::class.java)

                        // [START_EXCLUDE]
                        // Update RecyclerView
                        if(m is Msg) {
                            comments.add(m)
                            viewAdapter.notifyItemInserted(comments.size - 1)
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