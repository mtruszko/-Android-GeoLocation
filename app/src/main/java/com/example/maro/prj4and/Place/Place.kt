package com.example.maro.prj4and.Place

import android.location.Location

/**
 * Created by maro on 03.01.2018.
 */
data class Place(val name: String = "",
                 val desc: String = "",
                 val radius: Int = 0,
                 val la: Float = 0F,
                 val lo: Float = 0F) {
}