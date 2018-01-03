package com.example.maro.prj4and

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.maro.prj4and.AddPlace.AddPoiActivity
import com.example.maro.prj4and.Map.MapActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnMap.setOnClickListener { openMapActivity() }
        btnAddPlace.setOnClickListener { openAddPoiActivity() }
    }

    fun openMapActivity() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }

    fun openAddPoiActivity() {
        val intent = Intent(this, AddPoiActivity::class.java)
        startActivity(intent)
    }
}

