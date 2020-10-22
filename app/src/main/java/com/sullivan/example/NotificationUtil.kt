package com.sullivan.example

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context){
    //TODO: add a snooze action and a pending intent
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.channel_id)
    )
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("Android Example")
        .setContentText(messageBody)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}