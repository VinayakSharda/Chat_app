package com.example.chatapp

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class New_Message_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        supportActionBar?.title="Select User"
        var userlist: ArrayList<User> = ArrayList()
        val adapter=Adapter_activity(this,userlist)
        val recycle_view=findViewById<RecyclerView>(R.id.recycler_new_message)
        recycle_view.layoutManager=LinearLayoutManager(this)
        recycle_view.adapter=adapter
        FirebaseDatabase.getInstance().getReference().child("/users").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userlist.clear()
                    for(postSnapshot in snapshot.children){
                        val user=postSnapshot.getValue(User::class.java)
                        if(FirebaseAuth.getInstance().currentUser?.uid != user?.uid)
                            userlist.add(user!!)
                    }
                    adapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

}