package com.project.noticeme.common.utils.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

object PreferenceUtil {
    fun getPref(context: Context) =
        context.getSharedPreferences(PreferenceUtil.javaClass.name, Context.MODE_PRIVATE)
}

class SharedPreferenceManager @Inject constructor(
    private val pref: SharedPreferences
) {

    fun getInitialData() = pref.getBoolean(KEY_INITIAL_DATA, false)

    fun setInitialData(state: Boolean) {
        pref.edit { putBoolean(KEY_INITIAL_DATA, state) }
    }

    fun getIsOnBoardingShowed() = pref.getBoolean(KEY_ON_BOARDING, false)

    fun setOnBoardingShowed(state: Boolean) {
        pref.edit { putBoolean(KEY_ON_BOARDING, state) }
    }

    fun getNotificationSetting() = pref.getBoolean(KEY_NOTIFICATION, true)

    fun setNotificationSetting(state: Boolean) {
        pref.edit { putBoolean(KEY_NOTIFICATION, state) }
    }

    fun getIsOldNotificationUpdated() = pref.getBoolean(KEY_UPDATE_OLD_NOTIFICATION, false)

    fun setOldNotificationUpdated(state: Boolean) {
        pref.edit { putBoolean(KEY_UPDATE_OLD_NOTIFICATION, state) }
    }

    companion object {
        const val KEY_INITIAL_DATA = "initialData"
        const val KEY_ON_BOARDING = "onBoarding"
        const val KEY_NOTIFICATION = "notification"
        const val KEY_UPDATE_OLD_NOTIFICATION = "updateOldNotification"
    }
}
