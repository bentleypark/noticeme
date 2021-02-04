package com.project.noticeme.ui.guide.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.size.Scale
import com.project.noticeme.R
import com.project.noticeme.common.ex.launchActivityWithFinish
import com.project.noticeme.common.utils.preference.SharedPreferenceManager
import com.project.noticeme.databinding.FragmentGuideScreen4Binding
import com.project.noticeme.ui.MainActivity
import com.project.noticeme.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class GuideScreen4Fragment : BaseFragment<FragmentGuideScreen4Binding>() {

    @Inject
    lateinit var pref: SharedPreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGuideScreen4Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivGuideImg.load(R.drawable.guide_image4) {
                scale(Scale.FILL)
            }
            btnFinish.setOnClickListener {
                pref.setOnBoardingShowed(true)
                lifecycleScope.launchWhenCreated {
                    delay(1_000)
                    requireActivity().launchActivityWithFinish<MainActivity>()
                }
            }
        }
    }
}