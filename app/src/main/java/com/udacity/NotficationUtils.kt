package com.udacity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

private val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(title: String,status:String, applicationContext: Context) {

    val contentIntent = Intent(applicationContext, DetailActivity::class.java)

    contentIntent.putExtra("title",title)
    contentIntent.putExtra("status",status)

    contentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    contentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)


    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )


    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.channel_id)
    )

        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(applicationContext
            .getString(R.string.notification_title))
        .setContentText(title)


        .setAutoCancel(true)

        .addAction(R.drawable.ic_assistant_black_24dp,
            applicationContext.getString(R.string.notification_button),
           contentPendingIntent
        )


    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}