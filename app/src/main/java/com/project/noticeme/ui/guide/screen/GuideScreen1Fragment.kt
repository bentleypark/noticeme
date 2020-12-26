package com.project.noticeme.ui.guide.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import coil.size.Scale
import com.project.noticeme.R
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.databinding.FragmentGuideScreen1Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuideScreen1Fragment : Fragment(),
    ViewBindingHolder<FragmentGuideScreen1Binding> by ViewBindingHolderImpl() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentGuideScreen1Binding.inflate(layoutInflater), this) {
        binding!!.ivGuideImg.load(R.drawable.guide_image1) {
            scale(Scale.FILL)
        }
    }
}