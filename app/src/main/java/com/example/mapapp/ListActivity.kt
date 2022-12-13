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

        // First fetching of placelist
        val docRef = db.collection("places")
        docRef.get().addOnSuccessListener { documentSnapShot -> // could also use snapshotlistener
            placeList.clear()
            for(document in documentSnapShot.documents){
                val plc = document.toObject<Place?>()
                if(plc != null){
                    placeList.add(plc)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        // Add refresh button, How can I branch this off into a separate function with access to the adapter?
        val updateActionButton = findViewById<FloatingActionButton>(R.id.updateActionButton)
        updateActionButton.setOnClickListener {
            val docRef = db.collection("places")
            docRef.get().addOnSuccessListener { documentSnapShot ->
                placeList.clear()
                for(document in documentSnapShot.documents){
                    val plc = document.toObject<Place?>()
                    if(plc != null){
                        placeList.add(plc)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    fun updatePlaceList(){ // temporary test

    }

}