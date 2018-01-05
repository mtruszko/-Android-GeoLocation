package com.example.maro.prj4and.Map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maro.prj4and.Place.Place
import com.example.maro.prj4and.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*


class MapFragment : Fragment(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    val MY_PERMISSIONS_REQUEST_LOCATION = 98
    fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION)


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION)
            }
            return false
        } else {
            return true
        }
    }

    lateinit var mLocationRequest: LocationRequest
    lateinit var mGoogleApiClient: GoogleApiClient
    lateinit var mLastLocation: Location
    var mCurrLocationMarker: Marker? = null

    override fun onConnected(p0: Bundle?) {
        mLocationRequest = LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationChanged(location: Location?) {
        mLastLocation = location!!
        if (mCurrLocationMarker != null)
        {
            mCurrLocationMarker!!.remove()
        }
        val latLng = LatLng(location!!.getLatitude(), location!!.getLongitude())
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("Current Position")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
        mCurrLocationMarker = googleMap!!.addMarker(markerOptions)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.animateCamera(CameraUpdateFactory.zoomTo(11F))
        if (mGoogleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
        }
    }

    lateinit var mMapView: MapView
    private var googleMap: GoogleMap? = null

    var pois = listOf<Place>()
    set(value) {
        field = value
        if (googleMap != null) {
            onMapReady(googleMap)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_map, container, false)
        mMapView = rootView.findViewById(R.id.map)
        mMapView.onCreate(savedInstanceState)
        mMapView.onResume()

        try {
            MapsInitializer.initialize(activity.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mMapView.getMapAsync(this)

        return rootView
    }

    override fun onMapReady(gMap: GoogleMap?) {
        googleMap = gMap

//        buildGoogleApiClient()

        googleMap?.setMyLocationEnabled(true)

        for (poi in pois) {
            val llpoi = LatLng(poi.la.toDouble(), poi.lo.toDouble())
            gMap?.addMarker(MarkerOptions().position(llpoi).title(poi.name))
            gMap?.addCircle(CircleOptions().center(llpoi).radius(poi.radius.toDouble()))
            val cameraPosition = CameraPosition.Builder().target(llpoi).zoom(2f).build()
            gMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    @Synchronized protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        mGoogleApiClient.connect()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        fun newInstance(): MapFragment {
            val fragment = MapFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
