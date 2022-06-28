package com.example.chatapp

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val click=findViewById<Button>(R.id.register_button)
        click.setOnClickListener(View.OnClickListener {
            registration()
        })
        val already=findViewById<TextView>(R.id.already_have_account)
        already.setOnClickListener {
            val intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val photo=findViewById<Button>(R.id.photo)
        photo.setOnClickListener {
            val intent=Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,0)
        }
    }
    var select_photo: Uri?=null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode== Activity.RESULT_OK && data!=null)
        {
            select_photo=data.data
            val bitmap=MediaStore.Images.Media.getBitmap(contentResolver,select_photo)
            val bitmapdrawable=BitmapDrawable(bitmap)
            val circular_view=findViewById<CircleImageView>(R.id.circular_view)
            circular_view.setImageBitmap(bitmap)
            val photo=findViewById<Button>(R.id.photo)
            photo.alpha=0f
        }
    }
    private fun registration(){
        val name=findViewById<EditText>(R.id.person_name)
        val name1=name.text.toString()
        val email=findViewById<EditText>(R.id.email)
        val email1=email.text.toString();
        val password=findViewById<EditText>(R.id.password)
        val password1=password.text.toString()
        if(name1.isEmpty() || email1.isEmpty() || password1.isEmpty() || select_photo==null)
        {
            Toast.makeText(this,"Please enter all the information",Toast.LENGTH_SHORT).show()
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email1,password1)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    Log.d("RegisterActivity", "Registration Successful")
                    upload_firebase_photo()
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(this,"Enter valid data",Toast.LENGTH_SHORT).show()
            }
    }
    private fun upload_firebase_photo()
    {
        if(select_photo==null)return
        val filename=UUID.randomUUID().toString()
        val ref=FirebaseStorage.getInstance().getReference("/image/$filename")
        ref.putFile(select_photo!!)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Photo Uploaded Successful")
                ref.downloadUrl.addOnSuccessListener {
                    it.toString()
                    Log.d("RegisterActivity", "File Location:- $it")
                    savedatatodatabase(it.toString())
                }
            }
            .addOnFailureListener{

            }
    }
    private fun savedatatodatabase(ImageUrl :String){
        val name=findViewById<EditText>(R.id.person_name)
        val name1=name.text.toString()
        val uid=FirebaseAuth.getInstance().uid ?: ""
        val ref=FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user=User(uid,name1,ImageUrl)
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Data added successfully")
                val intent=Intent(this , Latest_Message_Activity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener{

            }
    }
}
