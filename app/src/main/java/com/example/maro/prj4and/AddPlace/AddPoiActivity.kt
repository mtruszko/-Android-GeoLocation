package com.example.maro.prj4and.AddPlace

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.maro.prj4and.Place.Place
import com.example.maro.prj4and.R
import kotlinx.android.synthetic.main.activity_add_poi.*
import java.lang.Integer.parseInt


class AddPoiActivity : AppCompatActivity() {
    var locationManager : LocationManager? = null
    val PERMISSION_ACCESS_COARSE_LOCATION: Int = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_poi)

        btnAdd.setOnClickListener { addPlaces() }

       setupPermissions()

//        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
//        val provider = locationManager!!.getBestProvider(Criteria(), false)
//        val location = locationManager!!.getLastKnownLocation(provider)

//        tvLocation.text = "" + location.longitude + ":" + location.latitude

//        try {
//            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener);
//        } catch(ex: SecurityException) {
//            Log.d("myTag", "Security Exception, no location available");
//        }
    }

    fun setupPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    PERMISSION_ACCESS_COARSE_LOCATION)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_ACCESS_COARSE_LOCATION -> if (!grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // All good!
            } else {
                Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    define the listener
//    private val locationListener: LocationListener = object : LocationListener {
//        override fun onLocationChanged(location: Location) {
//            tvLocation.text = "" + location.longitude + ":" + location.latitude
//        }
//        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
//        override fun onProviderEnabled(provider: String) {}
//        override fun onProviderDisabled(provider: String) {}
//    }

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

        val place = Place(etName.text.toString(), etDesc.text.toString(), rad)

//        FirebaseDB.saveToFirebase(place)
    }
}
