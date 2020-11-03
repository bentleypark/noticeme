package com.project.noticeme.common.ex

import android.app.Activity
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) = lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater.invoke(layoutInflater)
}

// 액티비티 전환 후 종료하는 함수
inline fun <reified T : Activity> Activity.launchActivityWithFinish() {
    val intent = Intent(this, T::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
    overridePendingTransition(0, 0)
    finish()
}

fun Fragment.makeToast(text: String) {
    val toast = Toast.makeText(activity, text, Toast.LENGTH_SHORT)

//    toast.setGravity(Gravity.BOTTOM, 0, 150)
    toast.show()
}