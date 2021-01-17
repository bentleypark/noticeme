package com.project.noticeme.ui.guide.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.size.Scale
import com.project.noticeme.R
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.common.ex.launchActivityWithFinish
import com.project.noticeme.common.utils.preference.SharedPreferenceManager
import com.project.noticeme.databinding.FragmentGuideScreen4Binding
import com.project.noticeme.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class GuideScreen4Fragment : Fragment(),
    ViewBindingHolder<FragmentGuideScreen4Binding> by ViewBindingHolderImpl() {

    @Inject
    lateinit var pref: SharedPreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentGuideScreen4Binding.inflate(layoutInflater), this) {

        binding?.apply {
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