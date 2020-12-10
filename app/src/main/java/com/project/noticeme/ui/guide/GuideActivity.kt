package com.project.noticeme.ui.guide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.project.noticeme.R
import com.project.noticeme.common.ex.viewBinding
import com.project.noticeme.common.utils.preference.SharedPreferenceManager
import com.project.noticeme.databinding.ActivityGuideBinding
import com.project.noticeme.ui.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GuideActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityGuideBinding::inflate)

    @Inject
    lateinit var pref: SharedPreferenceManager

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.checkIsInitialDataSet()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, GuideFragment())
        }.commit()
    }
}
