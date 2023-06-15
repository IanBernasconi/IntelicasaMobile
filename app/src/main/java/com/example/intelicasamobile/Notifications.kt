package com.example.intelicasamobile

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

class Notifications(
    var context: Context,
    var title: String,
    var message: String
) {
    private val channelID: String = "com.example.intelicasamobile"
    private val channelName: String = "Intelicasa"
    private val notificationManager =
        context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//    lateinit var notificationChannel: NotificationChannel
//    lateinit var notificationBuilder: NotificationCompat.Builder

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            channelID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Channel for device update notifications"
            enableLights(true)
            enableVibration(true)
            setShowBadge(true)
        }
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotification(id: Int = 100) {
        createNotificationChannel()

        val notification = NotificationCompat.Builder(context, channelID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_intelicasa_launcher)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(id, notification)
    }

//    fun fireNotification(){
//        notificationChannel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
//            enableLights(true)
//            enableVibration(true)
//        }
//        notificationManager.createNotificationChannel(notificationChannel)
//
//        val intent = Intent(context, MainActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//        notificationBuilder = NotificationCompat.Builder(context, channelID).apply {
//            setSmallIcon(R.mipmap.ic_intelicasa_launcher)
//            setContentTitle(title)
//            setContentText(message)
//            setAutoCancel(true)
//            addAction(R.mipmap.ic_intelicasa_launcher, "Open Message", pendingIntent)
//        }
//        notificationManager.notify(100, notificationBuilder.build())
//
//    }
}