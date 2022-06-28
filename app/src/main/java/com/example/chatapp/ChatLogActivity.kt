package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatLogActivity : AppCompatActivity() {
    var receiverroom:String?=null
    var senderroom:String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        val chat_view=findViewById<RecyclerView>(R.id.chatview)
        val send=findViewById<Button>(R.id.send)
        val newtext=findViewById<EditText>(R.id.new_text)

        val user_name=intent.getStringExtra("name")
        val uid=intent.getStringExtra("uid")
        val imageurl=intent.getStringExtra("imageurl")

        supportActionBar?.title =user_name

        var message_list:ArrayList<Message> = ArrayList()
        val adapter=messageAdapter(this, message_list)
        chat_view.layoutManager= LinearLayoutManager(this)
        chat_view.adapter=adapter

        senderroom=uid+FirebaseAuth.getInstance().currentUser?.uid
        receiverroom=FirebaseAuth.getInstance().currentUser?.uid+uid

        FirebaseDatabase.getInstance().getReference().child("chats").child(senderroom!!).child("message")
            .addValueEventListener( object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    message_list.clear()
                    for(postsnapshot in snapshot.children){
                        val message = postsnapshot.getValue(Message::class.java)
                        message_list.add(message!!)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        send.setOnClickListener{
            val message=newtext.text.toString()
            val messageObject =Message(message,uid)
            FirebaseDatabase.getInstance().getReference().child("chats").child(senderroom!!).child("message").push()
                .setValue(messageObject).addOnSuccessListener {
                    FirebaseDatabase.getInstance().getReference().child("chats").child(receiverroom!!).child("message").push()
                        .setValue(messageObject)

                }
            newtext.setText("")
        }
    }
}