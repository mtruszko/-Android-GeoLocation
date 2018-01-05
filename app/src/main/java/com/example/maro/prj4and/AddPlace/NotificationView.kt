package com.example.maro.prj4and.AddPlace

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.maro.prj4and.R
import android.widget.TextView
import android.content.Intent
import android.R.string.cancel
import android.app.NotificationManager
import android.content.Context


class NotificationView : AppCompatActivity() {

    // Declare Variable
//    lateinit var title: String
//    lateinit var text: String
//    lateinit var txttitle: TextView
//    lateinit var txttext: TextView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_view)

        // Create Notification Manager
//        val notificationmanager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        // Dismiss Notification
//        notificationmanager.cancel(0)

        // Retrive the data from MainActivity.java
//        val i = intent
//
//        title = i.getStringExtra("title")
//        text = i.getStringExtra("text")
//
//        // Locate the TextView
//        txttitle = title
//        txttext = text
//
//        // Set the data into TextView
//        txttitle.text = title
//        txttext.text = text
    }
}
