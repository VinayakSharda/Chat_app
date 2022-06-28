package com.example.chatapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val click=findViewById<Button>(R.id.login)
        click.setOnClickListener(View.OnClickListener {
            val email=findViewById<EditText>(R.id.login_email)
            val email1=email.text.toString();
            val password=findViewById<EditText>(R.id.login_password)
            val password1=password.text.toString()
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email1,password1)
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        Log.d("MainActivity", "Login Successful")
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener{
                    Toast.makeText(this,"Enter valid data", Toast.LENGTH_SHORT).show()
                }
        })
    }
}