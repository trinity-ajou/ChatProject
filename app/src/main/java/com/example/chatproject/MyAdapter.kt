package com.example.chatproject

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.my_text_view.*
import org.w3c.dom.Comment
import org.w3c.dom.Text
import java.util.ArrayList


public class MyAdapter(val comments : ArrayList<Msg>,val stEmail : String?) :

    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(var textView: View) : RecyclerView.ViewHolder(textView){
        var mTextView :TextView = textView.findViewById(R.id.mTextView)
    }

    override fun getItemViewType(position: Int): Int {
        if(comments.get(position).email.equals(stEmail)){
            return 1
            }else{
            return 2
        }

    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.MyViewHolder {
        // create a new view
        val textView : View
        if(viewType ==1){
            textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.right_text_view, parent, false) as View
        }else {
            textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.my_text_view, parent, false) as View

        }

        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(comments.get(position).text)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = comments.size
}