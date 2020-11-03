package com.project.noticeme.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.noticeme.R
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),
    ViewBindingHolder<FragmentHomeBinding> by ViewBindingHolderImpl() {

    private val viewModel: HomeViewModel by viewModels()
    private var consumableList = mutableListOf<ConsumableEntity>()
    private val listAdapter = ConsumableListAdapter(consumableList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(FragmentHomeBinding.inflate(layoutInflater), this) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()

        viewModel.consumableList.observe(
            viewLifecycleOwner, {

//                binding!!.apply {
//                    rvList.isVisible = false
//                    ivGuideMsg.isVisible = false
//                    emptyList.isVisible = false
//                }
//
//                if (it.isNotEmpty()) {
//                    binding.rvList.isVisible = true
//                    consumableList = it.toMutableList()
//                    listAdapter.addAll(consumableList)
//                } else {
//                    binding.ivGuideMsg.isVisible = true
//                    binding.emptyList.isVisible = true
//                }
            }
        )

        binding!!.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addConsumableFragment)
        }
    }

    private fun setUpView() {
        val size = resources.getDimensionPixelSize(R.dimen.material_item_size)
        binding!!.rvList.apply {
            adapter = listAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(SpaceDecoration(size))
        }
    }
}