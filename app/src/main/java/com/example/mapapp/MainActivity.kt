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

        // Firebase database and authentication
        auth = Firebase.auth

        db = FirebaseFirestore.getInstance()

        // Text fields
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        // Buttons
        val logInButton = findViewById<Button>(R.id.logInButton)
        val signUpButton = findViewById<Button>(R.id.signUpButton)

        // Button Listeners
        logInButton.setOnClickListener {
            logIn(emailEditText.text.toString(),passwordEditText.text.toString())
        }

        signUpButton.setOnClickListener {
            signUp(emailEditText.text.toString(),passwordEditText.text.toString())
        }

        // Button to view map without logging in
        val viewMapGuestButton = findViewById<Button>(R.id.directMapButton)

        viewMapGuestButton.setOnClickListener {
            val mapIntent = Intent(this, MapsActivity::class.java)
            startActivity(mapIntent)
        }

    }

    // Log in function
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
                } else{ // If log in failed
                    Toast.makeText(applicationContext,"Failed. ${task.exception}", Toast.LENGTH_LONG).show()
                }
            }
    }

    // Sign up function
    fun signUp(email : String, password : String){

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(applicationContext, "Field empty", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(applicationContext,"Account created!", Toast.LENGTH_LONG).show()
                } else{ // If sign up failed
                    Toast.makeText(applicationContext,"Failed. ${task.exception}", Toast.LENGTH_LONG).show()
                }
            }

    }

}