package com.project.noticeme.ui.guide.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import coil.size.Scale
import com.project.noticeme.R
import com.project.noticeme.databinding.FragmentGuideScreen3Binding
import com.project.noticeme.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuideScreen3Fragment : BaseFragment<FragmentGuideScreen3Binding>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGuideScreen3Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivGuideImg.load(R.drawable.guide_image3) {
            scale(Scale.FILL)
        }
    }
}