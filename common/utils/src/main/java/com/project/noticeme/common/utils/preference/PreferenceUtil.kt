package com.project.noticeme.common.utils.preference

import android.content.Context
import androidx.core.content.edit
import com.google.gson.GsonBuilder

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
