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
import android.content.IntentFilter
import android.app.PendingIntent
import android.content.Intent
import android.provider.Settings


class AddPoiActivity : AppCompatActivity() {
    var locationManager : LocationManager? = null
    val PERMISSION_ACCESS_FINE_LOCATION: Int = 99
    var loc: Location? = null
    set(value) {
        field = value
        setupTextViews()
    }

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

        if (!(locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)!!)) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }

        val location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location == null) {
            Toast.makeText(this, "No last known location. Aborting...",
                    Toast.LENGTH_LONG).show()

        } else {
            loc = location
        }

        try {
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener);
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available");
        }
    }

    fun setupTextViews() {
        tvLocation?.text = "Longitude: " + loc?.longitude + "\nLatitude: " + loc?.latitude
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
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

        addProximityAlert(loc!!.latitude, loc!!.longitude, rad.toFloat())
    }

    private fun addProximityAlert(latitude: Double, longitude: Double, radius: Float) {

        val intent = Intent(PROX_ALERT_INTENT)
        val proximityIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        locationManager?.addProximityAlert(
                latitude, // the latitude of the central point of the alert region
                longitude, // the longitude of the central point of the alert region
                radius, // the radius of the central point of the alert region, in meters
                -1, // time for this proximity alert, in milliseconds, or -1 to indicate no expiration
                proximityIntent // will be used to generate an Intent to fire when entry to or exit from the alert region is detected
        )

        val filter = IntentFilter(PROX_ALERT_INTENT)
        registerReceiver(ProximityIntentReceiver(), filter)
    }
    private val PROX_ALERT_INTENT = "ACTION_PROXIMITY_ALERT"
}