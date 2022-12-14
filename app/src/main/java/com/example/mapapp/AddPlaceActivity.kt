package com.example.mapapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.location.*
import com.google.firebase.firestore.FirebaseFirestore

class AddPlaceActivity : AppCompatActivity() {

    lateinit var db : FirebaseFirestore

    lateinit var placeNameEditText : EditText
    lateinit var descEditText : EditText
    lateinit var authorEditText : EditText

    lateinit var locationProvider : FusedLocationProviderClient
    lateinit var locationRequest : LocationRequest
    lateinit var locationCallback : LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)

        db = FirebaseFirestore.getInstance()

        placeNameEditText = findViewById(R.id.placeNameEditText)
        descEditText = findViewById(R.id.descEditText)
        authorEditText = findViewById(R.id.authorEditText)

        locationProvider = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(5000).build()
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult : LocationResult) {
                for(location in locationResult.locations){
                    Log.d("!!!!","lat: ${location.latitude} lng: ${location.longitude}")
                }
            }
        }

        val addButton = findViewById<Button>(R.id.uploadPlaceButton)
        addButton.setOnClickListener {
            addPlace(placeNameEditText.text.toString(), descEditText.text.toString(),
                10.1,10.1, authorEditText.text.toString())
        }
    }

    fun addPlace(place : String, desc : String, lat : Double, lng : Double, author : String){

        if(place.isEmpty() || desc.isEmpty() || author.isEmpty()){
            Toast.makeText(applicationContext,"Empty field",Toast.LENGTH_LONG).show()
            return
        }

        var item = Place(place, desc, lat, lng, author)
        db.collection("places").add(item)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(applicationContext,"Place added!",Toast.LENGTH_LONG).show()
                    finish()
                } else{
                    Toast.makeText(applicationContext,"Failed. ${task.exception}",Toast.LENGTH_LONG).show()
                }
            }
    }
}