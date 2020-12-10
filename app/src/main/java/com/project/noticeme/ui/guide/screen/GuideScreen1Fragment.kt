package com.project.noticeme.ui.guide.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.noticeme.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuideScreen1Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guide_screen1, container, false)
    }
}