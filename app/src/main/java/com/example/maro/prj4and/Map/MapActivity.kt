package com.example.maro.prj4and.Map

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.maro.prj4and.R
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val pageAdapter = PageAdapter(supportFragmentManager)

        pageAdapter.add(PlaceListFragment.newInstance(1), "Lista")
        pageAdapter.add(MapFragment.newInstance(), "Mapa")

        view_pager.adapter = pageAdapter
        tabs.setupWithViewPager(view_pager)
    }
}
