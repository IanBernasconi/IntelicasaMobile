package com.example.intelicasamobile

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class MyNotification(
    var context: Context, var title: String, var message: String){
    private val channelID: String = "com.example.intelicasamobile"
    private val channelName: String = "Intelicasa"
    private val notificationManager=context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationBuilder: NotificationCompat.Builder

    fun fireNotification(){
        notificationChannel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
            enableLights(true)
            enableVibration(true)
        }
        notificationManager.createNotificationChannel(notificationChannel)

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        notificationBuilder = NotificationCompat.Builder(context, channelID).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle(title)
            setContentText(message)
            setAutoCancel(true)
            addAction(R.drawable.ic_launcher_foreground, "Open Message", pendingIntent)
        }
        notificationManager.notify(100, notificationBuilder.build())

    }
}