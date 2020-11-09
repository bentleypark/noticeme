package com.project.noticeme.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.project.noticeme.R
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.databinding.FragmentAddCustomConsumableBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCustomConsumableFragment : Fragment(),
    ViewBindingHolder<FragmentAddCustomConsumableBinding> by ViewBindingHolderImpl() {

    private val viewModel: AddCustomConsumableViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(FragmentAddCustomConsumableBinding.inflate(layoutInflater), this) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.apply {
            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_addCustomConsumableFragment_pop)
            }
        }

    }

    companion object {
        fun newInstance() = AddCustomConsumableFragment()
    }
}