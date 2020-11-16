package com.project.noticeme.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import com.firebase.jobdispatcher.*
import timber.log.Timber

class JobSchedulerStart {

    companion object {
        fun start(context: Context) {
            Timber.d("JobSchedulerStart")
//            val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))
//            val myJob = dispatcher.newJobBuilder()
//                .setService(NotificationJobFireBaseService::class.java) // 잡서비스 등록
//                .setTag("Noticeme") // 태그 등록
////                .setRecurring(true) //재활용
//                .setLifetime(Lifetime.FOREVER) //다시켜도 작동을 시킬껀지?
////                .setTrigger(Trigger.executionWindow(0, 60)) //트리거 시간
////                .setReplaceCurrent(true)
////                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
//                .build()
//            dispatcher.mustSchedule(myJob)

            Timber.d("onStartJob")
            val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmBroadCastReceiver.javaClass)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                NotificationJobFireBaseService.REQUEST_CODE,
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
        }
    }
}