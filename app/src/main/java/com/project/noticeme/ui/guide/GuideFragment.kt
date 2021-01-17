package com.project.noticeme.ui.guide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.noticeme.common.ex.viewLifecycle
import com.project.noticeme.databinding.FragmentGuideBinding
import com.project.noticeme.ui.guide.screen.GuideScreen1Fragment
import com.project.noticeme.ui.guide.screen.GuideScreen2Fragment
import com.project.noticeme.ui.guide.screen.GuideScreen3Fragment
import com.project.noticeme.ui.guide.screen.GuideScreen4Fragment

class GuideFragment : Fragment() {

    private var binding: FragmentGuideBinding by viewLifecycle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGuideBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf<Any>(
            GuideScreen1Fragment(),
            GuideScreen2Fragment(),
            GuideScreen3Fragment(),
            GuideScreen4Fragment()
        )

        binding.viewPager.adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
    }
}