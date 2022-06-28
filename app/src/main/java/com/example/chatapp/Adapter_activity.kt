package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class Adapter_activity(val context: Context,val userlist: ArrayList<User>): RecyclerView.Adapter<Adapter_activity.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_name_new,parent, false))
    }
    override fun getItemCount(): Int {
        return userlist.size
 1  }
    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val user=userlist[position]
        holder.username1.text=user.User_Name
        Picasso.get().load(user.ImageUrl).into(holder.image1)
        holder.itemView.setOnClickListener{
            val intent= Intent(context,ChatLogActivity::class.java)
            intent.putExtra("name",user.User_Name)
            intent.putExtra("uid",user.uid)
            intent.putExtra("imageurl",user.ImageUrl)

            context.startActivity(intent)
        }
    }
    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val image1:ImageView=itemView.findViewById<ImageView>(R.id.new_image)
        val username1=itemView.findViewById<TextView>(R.id.new_name)
    }
}
