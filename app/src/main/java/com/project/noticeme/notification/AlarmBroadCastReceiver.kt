package com.project.noticeme.notification

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
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import androidx.annotation.CallSuper
import androidx.core.app.NotificationCompat
import com.project.noticeme.R
import com.project.noticeme.common.utils.preference.SharedPreferenceManager
import com.project.noticeme.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

abstract class HiltBroadcastReceiver : BroadcastReceiver() {
    @CallSuper
    override fun onReceive(context: Context, intent: Intent) {
    }
}

@AndroidEntryPoint
class AlarmBroadCastReceiver : HiltBroadcastReceiver() {

    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var pref: SharedPreferenceManager

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (pref.getNotificationSetting()) {
            notificationManager = context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

            createNotificationChannel()
            deliverNotification(context)
        }
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
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_content))
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
                FLAG_KEEP_SCREEN_ON or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                "noticeme:tag"
            ).apply {
                acquire(5000)
            }
        }
    }

    companion object {
        const val NOTIFICATION_ID = 33
    }
}