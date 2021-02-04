package com.project.noticeme.ui.base

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.project.noticeme.common.ex.viewLifecycle
import kotlinx.coroutines.Job

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    protected var binding: T by viewLifecycle()
    protected var searchJob: Job? = null
}