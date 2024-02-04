package com.abshtyfikant.fancal.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.abshtyfikant.fancal.R

class NotificationService (
    private var context: Context
) {
    private val notificationManager =
        context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(title: String, days: Int) {
        val name = "Main Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("Main Channel ID", name, importance)
        notificationManager.createNotificationChannel(channel)

//        val intent = Intent(context, MainActivity::class.java)
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(
//            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
//        )

        val contentText: String = if(days == 1){"Jutro odbędzie się wydarzenie $title"} else {"Pozostało $days dni do wydarzenia $title"}
        val builder = NotificationCompat.Builder(context, "Main Channel ID")
            .setSmallIcon(R.drawable.baseline_calendar_today_24)
            .setContentTitle("Wkrótce premiera $title")
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }
    }
}