package com.project.noticeme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.project.noticeme.common.ex.launchActivityWithFinish
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        moveToMainScreen()
    }

    private fun moveToMainScreen() {
        lifecycleScope.launchWhenCreated {
            delay(SPLASH_DELAY)
            launchActivityWithFinish<MainActivity>()
        }
    }

    companion object {
        private const val SPLASH_DELAY = 2_000L
    }
}