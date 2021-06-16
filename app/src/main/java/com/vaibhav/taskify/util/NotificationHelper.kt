package com.vaibhav.taskify.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.vaibhav.taskify.R
import javax.inject.Inject

class NotificationHelper @Inject constructor(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "TASKIFY_ID"
        const val CHANNEL_NAME = "TASKIFY"

    }

    private fun getNotificationId() = 0

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        setNotificationChannel()
    }

    private fun setNotificationChannel() {
        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)

    }

    fun showSilentNotification(title: String, description: String, pendingIntent: PendingIntent) {
        val notification =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setSound(null)
                .build()
        notificationManager.notify(getNotificationId(), notification)

    }

    fun showCompletedNotification(
        title: String,
        description: String,
        pendingIntent: PendingIntent
    ) {
        val notification =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .build()
        notificationManager.notify(getNotificationId(), notification)

    }


}