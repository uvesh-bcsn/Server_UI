package com.serverui.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.getSystemService
import androidx.core.net.toUri
import com.google.firebase.FirebaseApp
import com.serverui.R
import com.serverui.activity.MainActivity
import com.serverui.navigation.DEEPLINK_DOMAIN

@ExperimentalLayoutApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        createNotificationChannel()
        showNotification()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "channel_id",
            "channel_name",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager = getSystemService<NotificationManager>()!!
        notificationManager.createNotificationChannel(channel)
    }

    private fun showNotification() {
        val activityIntent = Intent(this, MainActivity::class.java).apply {
            data = "https://$DEEPLINK_DOMAIN/Sell/www.ixfi.com".toUri()
        }
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(activityIntent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }
        val notification = NotificationCompat.Builder(this, "channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("App Launched!")
            .setContentText("Tap to open deep link")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = getSystemService<NotificationManager>()!!
        notificationManager.notify(1, notification)

    }
}