package com.project.noticeme.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import kotlin.random.Random

class JobSchedulerStart {

    companion object {
        fun start(context: Context, duration: Long) {

            val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmBroadCastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(     // 2
                context, Random.nextInt(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                manager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + 15000,
                    pendingIntent
                )
            } else
                manager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + 15000,
                    pendingIntent
                )
        }
    }
}