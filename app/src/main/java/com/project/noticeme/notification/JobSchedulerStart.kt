package com.project.noticeme.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

class JobSchedulerStart {

    companion object {
        fun start(context: Context, duration: Long, id: Int) {

            val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmBroadCastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                manager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    duration,
                    pendingIntent
                )
            } else
                manager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    duration,
                    pendingIntent
                )
        }
    }
}