package com.project.noticeme.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.project.noticeme.R
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.common.ex.makeToast
import com.project.noticeme.common.utils.preference.PreferenceUtil
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.data.state.DataState
import com.project.noticeme.databinding.FragmentHomeBinding
import com.project.noticeme.ui.home.adapt.UserConsumableListAdapter
import com.project.noticeme.ui.home.utils.SwipeToDeleteCallback
import com.project.noticeme.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList

@AndroidEntryPoint
class HomeFragment : Fragment(),
    ViewBindingHolder<FragmentHomeBinding> by ViewBindingHolderImpl() {

    private val viewModel: HomeViewModel by viewModels()
    private var consumableList = mutableListOf<UserConsumableEntity>()
    private val listAdapter = UserConsumableListAdapter(consumableList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(FragmentHomeBinding.inflate(layoutInflater), this) {
        MobileAds.initialize(activity) {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        setUpObserve()
        registerDeleteDataItemAction()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserConsumableData()
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

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
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
                            if (!PreferenceUtil.getInitialData(requireContext())) {
                                makeToast(it.data)
                            }
                            PreferenceUtil.setInitialData(requireContext(), true)
                        }

                        is DataState.Loading -> {
                            lifecycleScope.launch {
                                binding!!.apply {
                                    progressCircular.isVisible = true
                                }
                                delay(1000)
                                makeToast("초기 데이터 설정 중입니다. 잠시만 기다려주세요!")
                            }
                        }
                    }
                }
            )

            consumableList?.observe(
                viewLifecycleOwner, {
                    binding!!.apply {
                        rvList.isVisible = false
                        ivGuideMsg.isVisible = false
                        emptyList.isVisible = false
                        progressCircular.isVisible = false
                    }

                    if (it != null) {
                        when (it) {
                            is DataState.Success<List<UserConsumableEntity>> -> {
                                binding.progressCircular.isVisible = false
                                if (it.data.isNotEmpty()) {
                                    lifecycleScope.launch {
                                        binding.rvList.isVisible = true
                                        listAdapter.addAll(it.data.sortedByDescending { item -> item.priority }
                                            .toMutableList())
                                    }
                                } else {
                                    binding.ivGuideMsg.isVisible = true
                                    binding.emptyList.isVisible = true
                                }
                            }

                            is DataState.Loading -> {
                                binding.apply {
                                    progressCircular.isVisible = true
                                    rvList.isVisible = false
                                }
                            }
                        }
                    }
                }
            )
        }
    }

    private fun registerDeleteDataItemAction() {
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                viewModel.delete(listAdapter.removeAt(viewHolder.adapterPosition))
                viewModel.deleteResult.observe(
                    viewLifecycleOwner,
                    {
                        when (it) {
                            is DataState.Success<Boolean> -> {
                                makeToast("소모품이 삭제돠었습니다.")
                            }
                        }
                    }
                )
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding!!.rvList)
    }
}