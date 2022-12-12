package com.example.mapapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        var placeList = initialisePlaceList()

    }

    fun initialisePlaceList() : List<Place>{
        return mutableListOf<Place>(Place("Gothenburg","Big",11.1,12.1,"Bob"),
                                    Place("Malmö","Smaller",8.2,2.2,"Ron"),
                                    Place("Lund","Cold",6.3,3.3,"Red"))
    }

}