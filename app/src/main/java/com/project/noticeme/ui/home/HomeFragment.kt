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
import com.project.noticeme.common.ex.makeToast
import com.project.noticeme.common.utils.preference.PreferenceUtil
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.state.DataState
import com.project.noticeme.databinding.FragmentHomeBinding
import com.project.noticeme.ui.home.adapt.ConsumableListAdapter
import com.project.noticeme.ui.home.viewmodel.HomeViewModel
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
        setUpObserve()
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

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addConsumableFragment)
        }
    }

    private fun setUpObserve() {
        viewModel.apply {

            dataState.observe(
                viewLifecycleOwner, {
                    when (it) {
                        is DataState.Success<String> -> {
                            binding!!.apply {
                                progressCircular.isVisible = false
                            }
                            PreferenceUtil.setInitialData(requireContext(), true)
                            makeToast(it.data)
                        }

                        is DataState.Loading -> {
                            binding!!.apply {
                                progressCircular.isVisible = true
                            }
                            makeToast("초기 데이터 설정 중입니다. 잠시만 기다려주세요!")
                        }
                    }
                }
            )

            consumableList.observe(
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
        }
    }
}