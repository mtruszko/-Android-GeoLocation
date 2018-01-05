package com.example.maro.prj4and.AddPlace

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.util.Log
import com.example.maro.prj4and.R


/**
 * Created by maro on 05.01.2018.
 */

class ProximityIntentReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val key = LocationManager.KEY_PROXIMITY_ENTERING

        val entering = intent.getBooleanExtra(key, false)
        val name = intent.getStringExtra("PROX_ALERT_INTENT_NAME")
        val desc = intent.getStringExtra("PROX_ALERT_INTENT_DESC")


        if (entering) {
            Notification(context, "Entering" + name + " " + desc)
        } else {
            Notification(context, "Exiting" + name + " " + desc)
        }
    }

    fun Notification(context: Context, message: String) {
        val strtitle = "proj4and"
        val intent = Intent(context, NotificationView::class.java)
        intent.putExtra("title", strtitle)
        intent.putExtra("text", message)
        val pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = Notification.Builder(context)
                .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                .setTicker(message)
                .setContentTitle("proj4and")
                .setContentText(message)
                .addAction(R.drawable.cast_ic_notification_small_icon, "Action Button", pIntent)
                .setContentIntent(pIntent)
                .setAutoCancel(true)

        val notificationmanager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationmanager.notify(0, builder.build())
    }
}