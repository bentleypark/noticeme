package com.project.noticeme.ui.guide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.project.noticeme.R
import com.project.noticeme.common.ex.launchActivityWithFinish
import com.project.noticeme.common.ex.viewBinding
import com.project.noticeme.common.utils.preference.SharedPreferenceManager
import com.project.noticeme.databinding.ActivityGuideBinding
import com.project.noticeme.ui.MainActivity
import com.project.noticeme.ui.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class GuideActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityGuideBinding::inflate)

    @Inject
    lateinit var pref: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        OnBoardingAdapter(centeredImageResources).also {
            binding.introScreenViewPager.adapter = it
        }

        binding.introScreenViewPager.apply {
            registerOnPageChangeCallback(viewPagerOnChangeCallback)
            setPageTransformer(DepthPageTransformer())
        }
    }

    private val viewPagerOnChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {

            binding.ivCenterImg.setImageResource(centeredImageResources[position])
            binding.ivBottomDot.setImageResource(bottomDotImageResource[position])
            if (position == 3) {
                pref.setOnBoardingShowed(true)
                lifecycleScope.launchWhenCreated {
                    delay(2_000)
                    launchActivityWithFinish<MainActivity>()
                }
            }
        }
    }

    companion object {
        private val centeredImageResources = mutableListOf(
            R.drawable.guide_image1,
            R.drawable.guide_image2,
            R.drawable.guide_image3,
            R.drawable.guide_image4
        )

        private val bottomDotImageResource = mutableListOf(
            R.drawable.ic_guide_dot_1,
            R.drawable.ic_guide_dot_2,
            R.drawable.ic_guide_dot_3,
            R.drawable.ic_guide_dot_4
        )
    }
}
