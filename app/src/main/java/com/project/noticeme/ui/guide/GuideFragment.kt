package com.project.noticeme.ui.guide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.databinding.FragmentGuideBinding
import com.project.noticeme.ui.guide.screen.GuideScreen1Fragment
import com.project.noticeme.ui.guide.screen.GuideScreen2Fragment
import com.project.noticeme.ui.guide.screen.GuideScreen3Fragment
import com.project.noticeme.ui.guide.screen.GuideScreen4Fragment

class GuideFragment : Fragment(),
    ViewBindingHolder<FragmentGuideBinding> by ViewBindingHolderImpl() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentGuideBinding.inflate(layoutInflater), this) {


        val fragmentList = arrayListOf<Any>(
            GuideScreen1Fragment(),
            GuideScreen2Fragment(),
            GuideScreen3Fragment(),
            GuideScreen4Fragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding!!.viewPager.adapter = adapter
    }
}