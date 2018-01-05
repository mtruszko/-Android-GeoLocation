package com.example.maro.prj4and.AddPlace

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.support.v7.app.NotificationCompat
import android.util.Log
import com.example.maro.prj4and.R.mipmap.ic_launcher
import com.example.maro.prj4and.MainActivity
import com.example.maro.prj4and.R
import com.example.maro.prj4and.R.mipmap.ic_launcher
import android.app.NotificationChannel






/**
 * Created by maro on 05.01.2018.
 */

class ProximityIntentReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val key = LocationManager.KEY_PROXIMITY_ENTERING

        val entering = intent.getBooleanExtra(key, false)

        if (entering) {
            Log.d(javaClass.simpleName, "entering")
        } else {
            Log.d(javaClass.simpleName, "exiting")
        }

        Notification(context, "ssssss")
    }

    fun Notification(context: Context, message: String) {
        val strtitle = "aaa"
        val intent = Intent(context, NotificationView::class.java)
        intent.putExtra("title", strtitle)
        intent.putExtra("text", message)
        val pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = Notification.Builder(
                context)
                .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                .setTicker(message)
                .setContentTitle("aaa")
                .setContentText(message)
                .addAction(R.drawable.cast_ic_notification_small_icon, "Action Button", pIntent)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setChannelId("dddd")

        val notificationmanager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationmanager.notify(0, builder.build())
    }
}