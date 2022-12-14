package com.example.mapapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mapapp.databinding.ActivityMapsBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = FirebaseFirestore.getInstance()

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Move the camera to the center of the map
        val center = LatLng(0.0, 0.0)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(center))

        placeMarkersFromFirebase()
    }

    fun placeMarkersFromFirebase(){
        var placeList = mutableListOf<Place>()
        val docRef = db.collection("places")
        docRef.get().addOnSuccessListener { documentSnapShot ->
            for(document in documentSnapShot.documents){
                val plc = document.toObject<Place?>()
                if(plc != null){
                    placeList.add(plc)
                }
            }

            for(plc : Place in placeList){
                var position = LatLng(plc.lat!!,plc.lng!!)
                var marker = mMap.addMarker(
                    MarkerOptions().position(position).title(plc.name).snippet(plc.desc))
            }

        }

    }
}