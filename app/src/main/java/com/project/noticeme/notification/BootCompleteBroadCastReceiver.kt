package com.project.noticeme.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.project.noticeme.ui.MainActivity

class BootCompleteBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals("android.intent.action.BOOT_COMPLETED")) {
            val bootCompleteIntent = Intent(context, MainActivity::class.java)
            bootCompleteIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context!!.startActivity(bootCompleteIntent)
        }
    }
}