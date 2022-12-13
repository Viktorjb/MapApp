package com.example.mapapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ListActivity : AppCompatActivity() {

    lateinit var db : FirebaseFirestore
    var placeList = mutableListOf<Place>() // temporary test


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        db = FirebaseFirestore.getInstance()

        //var placeList = mutableListOf<Place>()

        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = PlaceRecycleAdapter(this, placeList)

        recyclerView.adapter = adapter

        //Add place button

        val fActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fActionButton.setOnClickListener {
            val intent = Intent(this, AddPlaceActivity::class.java)
            startActivity(intent)
        }

        //updatePlaceList() // temporary test
        val docRef = db.collection("places")

        docRef.get().addOnSuccessListener { documentSnapShot ->
            for(document in documentSnapShot.documents){
                Log.d("!!!!", "I got here")
                val plc = document.toObject<Place?>()
                Log.d("!!!!", "${plc?.name}, ${plc?.desc}, ${plc?.author}")
                if(plc != null){
                    placeList.add(plc)
                    adapter.notifyDataSetChanged()
                }

            }
        }
        //test ends here, giving Place.kt default values seemed to have fixed it

    }

    fun initialisePlaceList() : List<Place>{

        val docRef = db.collection("places")
        var placeList = mutableListOf<Place>()

        docRef.get().addOnSuccessListener { documentSnapShot ->
            for(document in documentSnapShot.documents){
                val plc = document.toObject<Place>()
                if(plc != null){
                    placeList.add(plc)
                }

            }
        }

        return placeList

        /*return mutableListOf<Place>(Place("Gothenburg","Big",11.1,12.1,"Bob"),
                                    Place("Malmö","Smaller",8.2,2.2,"Ron"),
                                    Place("Lund","Cold",6.3,3.3,"Red"))*/
    }

    fun updatePlaceList(){ // temporary test

    }

}