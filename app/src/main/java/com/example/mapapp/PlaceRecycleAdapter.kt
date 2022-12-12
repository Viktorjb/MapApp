package com.example.mapapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlaceRecycleAdapter(val context: Context, val places : List<Place>): RecyclerView.Adapter<PlaceRecycleAdapter.ViewHolder>() {

    var layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]
        holder.placeNameTextView.text = place.name
        holder.latlangTextView.text = "Lat: ${place.lat} Lng: ${place.lng}"
        holder.authorTextView.text = "Created by ${place.author}"
    }

    override fun getItemCount(): Int {
        return places.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var placeNameTextView = itemView.findViewById<TextView>(R.id.placeNameTextView)
        var latlangTextView = itemView.findViewById<TextView>(R.id.latlangTextView)
        var authorTextView = itemView.findViewById<TextView>(R.id.authorTextView)
    }
}