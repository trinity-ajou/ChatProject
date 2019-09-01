package com.example.chatproject

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import java.util.ArrayList


public class ChatAdapter(val data : ArrayList<ChatList_Data>) :
    RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(var textView: View) : RecyclerView.ViewHolder(textView){
        var mTextEmail :TextView = textView.findViewById(R.id.iv_roomid)
        var mText :TextView = textView.findViewById(R.id.iv_roomtext)
    }


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ChatAdapter.MyViewHolder {
        // create a new view
        val textView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.chatlist_data, parent, false) as View
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextEmail.setText(data.get(position).email)
        holder.mText.setText(data.get(position).text)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size
}
