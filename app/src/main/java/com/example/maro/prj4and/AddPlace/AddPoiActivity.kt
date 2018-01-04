package com.example.maro.prj4and.AddPlace

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.maro.prj4and.Place.FirebaseDB
import com.example.maro.prj4and.Place.Place
import com.example.maro.prj4and.R
import kotlinx.android.synthetic.main.activity_add_poi.*
import java.lang.Integer.parseInt


class AddPoiActivity : AppCompatActivity() {
    var locationManager : LocationManager? = null
    val PERMISSION_ACCESS_FINE_LOCATION: Int = 99
    var loc: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_poi)

        btnAdd.setOnClickListener { addPlaces() }

       setupPermissions()
    }

    fun setupPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_ACCESS_FINE_LOCATION)
        } else {
            getLocation()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_ACCESS_FINE_LOCATION -> if (!grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               getLocation()
            } else {
                Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getLocation() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
//        val provider = locationManager!!.getBestProvider(Criteria(), false)
//        val location = locationManager!!.getLastKnownLocation(provider)

//        tvLocation.text = "" + location.longitude + ":" + location.latitude

        try {
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener);
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available");
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            tvLocation.text = "Longitude: " + location.longitude + "\nLatitude: " + location.latitude
            loc = location
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    fun addPlaces() {
        if (etName.text.isEmpty() || etDesc.text.isEmpty() || etRadius.text.isEmpty()) {
            val toast = Toast.makeText(this, "Invalid data", 3)
            toast.show()
            return
        }

        val rad: Int? = try { parseInt(etRadius.text.toString()) } catch (e: NumberFormatException) { null }

        if (rad == null) {
            val toast = Toast.makeText(this, "Invalid data", 3)
            toast.show()
            return
        }

        if (loc == null) {
            val toast = Toast.makeText(this, "No location Data", 3)
            toast.show()
        }

        val place = Place(etName.text.toString(), etDesc.text.toString(), rad, loc!!.latitude.toFloat(), loc!!.longitude.toFloat())

        FirebaseDB.saveToFirebase(place)
    }
}
