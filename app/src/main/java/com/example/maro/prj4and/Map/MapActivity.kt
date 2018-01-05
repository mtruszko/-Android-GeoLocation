package com.example.maro.prj4and.Map

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.maro.prj4and.Place.FirebaseDB
import com.example.maro.prj4and.Place.Place
import com.example.maro.prj4and.R
import kotlinx.android.synthetic.main.activity_map.*
import com.google.android.gms.maps.SupportMapFragment



class MapActivity : AppCompatActivity() {

    lateinit var pageAdapter: PageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        FirebaseDB.readListOfProducts { places ->
            update(places)
        }

        pageAdapter = PageAdapter(supportFragmentManager)
        pageAdapter.add(PlaceListFragment.newInstance(1), "Lista")
        pageAdapter.add(MapFragment.newInstance(), "Mapa")
        view_pager.adapter = pageAdapter
        tabs.setupWithViewPager(view_pager)
    }

    fun update(places: List<Place>) {
        (pageAdapter.getItem(0) as PlaceListFragment).pois = places
        (pageAdapter.getItem(1) as MapFragment).pois = places
    }
}
