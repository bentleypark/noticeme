package com.project.noticeme.ui.add

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.noticeme.R
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.databinding.FragmentAddComsumableBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddConsumableFragment : Fragment(),
    ViewBindingHolder<FragmentAddComsumableBinding> by ViewBindingHolderImpl() {

    companion object {
        fun newInstance() = AddConsumableFragment()
    }

    private lateinit var viewModel: AddConsumableViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(FragmentAddComsumableBinding.inflate(layoutInflater), this) {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddConsumableViewModel::class.java)
        // TODO: Use the ViewModel
    }
}