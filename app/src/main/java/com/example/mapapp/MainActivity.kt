package com.example.mapapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    lateinit var db : FirebaseFirestore

    lateinit var emailEditText : EditText
    lateinit var passwordEditText : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        db = FirebaseFirestore.getInstance() // Not like the video

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        val logInButton = findViewById<Button>(R.id.logInButton)
        val signUpButton = findViewById<Button>(R.id.signUpButton)


        logInButton.setOnClickListener {
            logIn(emailEditText.text.toString(),passwordEditText.text.toString())
        }

        signUpButton.setOnClickListener {
            signUp(emailEditText.text.toString(),passwordEditText.text.toString())
        }

    }

    fun logIn(email : String, password : String){
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(applicationContext, "Field empty", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(applicationContext,"Logged in.", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, ListActivity::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(applicationContext,"Failed. ${task.exception}", Toast.LENGTH_LONG).show()
                }
            } // from 20
    }

    fun signUp(email : String, password : String){

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(applicationContext, "Field empty", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(applicationContext,"Account created!", Toast.LENGTH_LONG).show()
                } else{
                    Toast.makeText(applicationContext,"Failed. ${task.exception}", Toast.LENGTH_LONG).show()
                }
            }

    }

}