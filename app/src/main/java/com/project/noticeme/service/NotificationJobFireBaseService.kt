package com.project.noticeme.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import timber.log.Timber

class NotificationJobFireBaseService : JobService() {
    override fun onStartJob(job: JobParameters): Boolean {
        Timber.d("onStartJob")
        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmBroadCastReceiver.javaClass)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 10000,
                pendingIntent
            )
            Timber.d("setExactAndAllowWhileIdle")
        } else
            manager.setExact(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 10000,
                pendingIntent
            )

        return false
    }

    override fun onStopJob(job: JobParameters): Boolean {
        return false
    }

    companion object {
        const val REQUEST_CODE = 111
    }
}