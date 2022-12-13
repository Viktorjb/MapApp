package com.example.mapapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        var placeList = initialisePlaceList()

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

    }

    fun initialisePlaceList() : List<Place>{
        return mutableListOf<Place>(Place("Gothenburg","Big",11.1,12.1,"Bob"),
                                    Place("Malmö","Smaller",8.2,2.2,"Ron"),
                                    Place("Lund","Cold",6.3,3.3,"Red"))
    }

}