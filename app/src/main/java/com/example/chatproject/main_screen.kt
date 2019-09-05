package com.example.chatproject

import android.content.Intent
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
import com.google.firebase.auth.FirebaseAuth
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

        setTitle("매칭")

        var user = FirebaseAuth.getInstance().getCurrentUser()
        var database = FirebaseDatabase.getInstance()
        var emailadd: String? = ""
        var usruid: String? = ""
        var sex: String? = ""
        var opsex: String? = ""
        var chatuid: String? = ""
        var usrinfo = Hashtable<String, String>()
        var matchingQ = Hashtable<String, String>()
        if (user != null){
            emailadd = user.getEmail()
            usruid = user.uid
            val myRef = database.getReference().child("usrInfo").child(usruid.toString())
            usrinfo.put("chatUID", "-1")
            usrinfo.put("email", emailadd)
            if (emailadd == "ssoo2024@naver.com")
            {
                sex = "male"
                opsex = "female"
            }
            else
            {
                sex = "female"
                opsex = "male"
            }
            usrinfo.put("sex", sex)
            myRef.setValue(usrinfo)
        }
        random_match_button.setOnClickListener {
            val match_Intent = Intent(this, ChatActivity::class.java)
            val myRef = database.getReference().child("waitList").child(sex.toString()) //사용자의 성별 가져옴
            val opRef = database.getReference().child("waitList").child(opsex.toString())
            opRef.addChildEventListener(object : ChildEventListener{
                override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                    val obUID = dataSnapshot.getValue(String::class.java)
                    if (obUID == null) //waitlist에 이성이 없다면
                    {
                        Toast.makeText(baseContext, "랜덤 매칭 중", Toast.LENGTH_SHORT).show()
                        matchingQ.put(usruid, "1") //대기 목록에 넣는거임
                        myRef.setValue(matchingQ)  //이하 동문
                    }
                    else//waitlist에 이성이 있다면
                    {
                        database.getReference().child("usrInfo").child(obUID).child("chatUID").push().setValue("1")
                        Toast.makeText(baseContext, "매칭 되었습니다!", Toast.LENGTH_SHORT).show()
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
            //리스너 만들어서 자신의 유저정보안에 채팅방 uid가 생성되면 푸쉬 메시지

            startActivity(match_Intent) //채팅시작하는거 -> 수정해야됨(채팅 리스트뷰랑 연결)
            //채팅방 uid를 생성하여 상대방과 자신의 유저정보에 등록 및 푸시 메시지
        }
        group_match_button.setOnClickListener {
            val g_intent = Intent(this,GroupMatch_main::class.java)
            startActivity(g_intent)
        }

        bottom_menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.matching -> {
                val fragmentA = MatchingFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_screen_frame, fragmentA)
                    .commit()
                setTitle("매칭")
                return@OnNavigationItemSelectedListener true
            }
            R.id.chat_room -> {
                val fragmentB = ChatListFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_screen_frame, fragmentB)
                    .commit()

                setTitle("대화방")
                random_match_button.setVisibility(View.GONE)
                group_match_button.setVisibility(View.GONE)
                return@OnNavigationItemSelectedListener true
            }
            R.id.more -> {
                val fragmentC = MoreFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_screen_frame, fragmentC)
                    .commit()
                setTitle("설정")
                random_match_button.setVisibility(View.GONE)
                group_match_button.setVisibility(View.GONE)
                return@OnNavigationItemSelectedListener true
            }
            /*
            R.id.group -> {
                val fragmentD = GroupFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_screen_frame, fragmentD)
                    .commit()
                random_match_button.setVisibility(View.GONE)
                group_match_button.setVisibility(View.GONE)
                return@OnNavigationItemSelectedListener true
            }
            */
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

        /*
        button_to.setOnClickListener{
            val chat_intent = Intent(getActivity(), GroupMatch_main::class.java)
            startActivity(chat_intent)
        }
*/
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var database = FirebaseDatabase.getInstance()
        var myRef = database.getReference("Room")

        viewManager = LinearLayoutManager(activity)
        viewAdapter = ChatAdapter(context, room_list)

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
