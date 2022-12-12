package com.example.mapapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    lateinit var emailEditText : EditText
    lateinit var passwordEditText : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        val logInButton = findViewById<Button>(R.id.logInButton)
        val signUpButton = findViewById<Button>(R.id.signUpButton)


        signUpButton.setOnClickListener {
            signUp(emailEditText.text.toString(),passwordEditText.text.toString())
        }

    }

    fun signUp(email : String, password : String){

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(applicationContext, "Field empty", Toast.LENGTH_SHORT).show()
            return
        }

    }

}