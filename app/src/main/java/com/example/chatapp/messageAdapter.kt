package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class messageAdapter(val context: Context, val messagelist :ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val item_receive=1;
    val item_sent=2;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==1){
            val view:View = LayoutInflater.from(parent.context).inflate(R.layout.activity_recieve, parent, false)
            return ReceiveViewHolder(view)
        }
        else{
            val view:View = LayoutInflater.from(parent.context).inflate(R.layout.activity_sent, parent, false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage=messagelist[position]
        if(holder.javaClass==SentViewHolder::class.java){
            val viewHolder=holder as SentViewHolder
            holder.sentmessage.text=currentMessage.message
        }
        else{
            val viewHolder=holder as ReceiveViewHolder
            holder.recievemessage.text=currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage=messagelist[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId))
        {
            return item_receive
        }
        else{
            return item_sent
        }
    }
    override fun getItemCount(): Int {
        return messagelist.size
    }
    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val sentmessage=itemView.findViewById<TextView>(R.id.sentmessage)
    }
    class ReceiveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val recievemessage=itemView.findViewById<TextView>(R.id.recievemessage)
    }


}