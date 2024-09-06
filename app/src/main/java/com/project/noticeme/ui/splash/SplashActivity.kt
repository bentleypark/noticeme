package com.project.noticeme.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.project.noticeme.ui.MainActivity
import com.project.noticeme.R
import com.project.noticeme.common.ex.launchActivityWithFinish
import com.project.noticeme.common.utils.preference.SharedPreferenceManager
import com.project.noticeme.ui.guide.GuideActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var pref: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        moveToMainScreen()
    }

    private fun moveToMainScreen() {
        lifecycleScope.launch {
            delay(SPLASH_DELAY)
            if (pref.getIsOnBoardingShowed()) {
                launchActivityWithFinish<MainActivity>()
            } else {
                launchActivityWithFinish<GuideActivity>()
            }
        }
    }

    companion object {
        private const val SPLASH_DELAY = 2_000L
    }
}