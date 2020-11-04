package com.project.noticeme.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.databinding.FragmentConsumableDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConsumableDetailFragment : Fragment(),
    ViewBindingHolder<FragmentConsumableDetailBinding> by ViewBindingHolderImpl() {

    private val viewModel: ConsumableDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(FragmentConsumableDetailBinding.inflate(layoutInflater), this){
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    companion object {
        fun newInstance() = ConsumableDetailFragment()
    }
}