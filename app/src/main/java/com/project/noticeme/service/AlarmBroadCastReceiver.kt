package com.project.noticeme.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.PowerManager
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import androidx.core.app.NotificationCompat
import com.project.noticeme.R
import com.project.noticeme.ui.MainActivity

class AlarmBroadCastReceiver : BroadcastReceiver() {

    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {

        notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        createNotificationChannel()

        deliverNotification(context)

//        val builder = NotificationCompat.Builder(context!!, "test")
//            .setSmallIcon(R.drawable.icon)
//            .setContentTitle("Title")  //알람 제목
//            .setContentText("Text") //알람 내용
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//
//        val notificationManager = NotificationManagerCompat.from(context)
//        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "NOTICEME",
                "Stand up notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationChannel.description = "AlarmManager Tests"
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }
    }

    private fun deliverNotification(context: Context) {
        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder =
            NotificationCompat.Builder(context, "NOTICEME")
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("소모품 교체 알림")
                .setContentText("오늘 날짜로 교체할 소모품이 있습니다. 확인해주세요!")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)


        notificationManager.notify(NOTIFICATION_ID, builder.build())
        wakeUp(context)
    }

    private fun wakeUp(context: Context) {

        (context.getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(
                PowerManager.SCREEN_DIM_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, "noticeme:tag"
            ).apply {
                acquire(5000)
            }
        }
    }

    companion object {
        const val NOTIFICATION_ID = 33
    }
}