package com.project.noticeme.common.utils.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

object PreferenceUtil {
    fun getPref(context: Context) =
        context.getSharedPreferences(PreferenceUtil.javaClass.name, Context.MODE_PRIVATE)

    fun setToken(context: Context, token: String) {
        val pref = getPref(context)
        pref.edit { putString("token", token) }
    }

    fun getToken(context: Context) = getPref(context).getString("token", "")

    fun getPhaseData(context: Context) = getPref(context).getString("phaseData", "")

    fun setInitialData(context: Context, state: Boolean) {
        val pref = getPref(context)
        pref.edit { putBoolean("initialData", state) }
    }

    fun getInitialData(context: Context) = getPref(context).getBoolean("initialData", false)

//    fun setPhaseData(context: Context, phaseData: PhraseData) {
//        val pref = getPref(context)
//        val gson = GsonBuilder().create()
//
//        pref.edit {
//            putString("phaseData", gson.toJson(phaseData))
//        }
//    }
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
        pref.edit { putBoolean(KEY_ON_BOARDING, state) }
    }

    companion object {
        const val KEY_INITIAL_DATA = "initialData"
        const val KEY_ON_BOARDING = "onBoarding"
        const val KEY_NOTIFICATION = "notification"
    }
}
