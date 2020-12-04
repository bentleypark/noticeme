package com.project.noticeme.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.project.noticeme.R
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.common.ex.*
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.state.DataState
import com.project.noticeme.databinding.FragmentCategoryDetailBinding
import com.project.noticeme.ui.category.viewmodel.CategoryDetailViewModel
import com.project.noticeme.ui.home.SpaceDecoration
import com.project.noticeme.ui.category.adapt.ConsumableListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryDetailFragment : Fragment(),
    ViewBindingHolder<FragmentCategoryDetailBinding> by ViewBindingHolderImpl() {

    private val viewModel: CategoryDetailViewModel by viewModels()
    private lateinit var listAdapter: ConsumableListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(FragmentCategoryDetailBinding.inflate(layoutInflater), this) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemTitle = arguments?.getString(ARGS_KEY)

        binding!!.tvTitle.text = itemTitle
        viewModel.findConsumableWithCategory(itemTitle!!)
        viewModel.apply {
            consumableList.observe(viewLifecycleOwner,
                {
                    when (it) {
                        is DataState.Success<List<ConsumableEntity>> -> {
                            binding.apply {
                                progressCircular.makeGone()
                            }

                            if (it.data.isEmpty()) {
                                binding.apply {
                                    rvList.makeGone()
                                    aboveLayoutTitle.makeGone()
                                    emptyList.makeVisible()
                                }
                            } else {
                                binding.apply {
                                    rvList.makeVisible()
                                    aboveLayoutTitle.makeVisible()
                                    emptyList.makeGone()
                                }
                            }

                            listAdapter = ConsumableListAdapter(it.data.toMutableList(), viewModel, requireContext())

                            val size = resources.getDimensionPixelSize(R.dimen.material_item_size)
                            binding.rvList.apply {
                                adapter = listAdapter
                                layoutManager =
                                    LinearLayoutManager(
                                        context,
                                        LinearLayoutManager.VERTICAL,
                                        false
                                    )
                                setHasFixedSize(true)
                                addItemDecoration(SpaceDecoration(size))
                                runLayoutAnimation()
                            }
                        }
                        is DataState.Loading -> {
                            binding.apply {
                                progressCircular.isVisible = true
                                rvList.isVisible = false
                            }
                        }
                    }
                })

            dataState.observe(viewLifecycleOwner, {
                when (it) {
                    is DataState.Success<Boolean> -> {
//                        makeToast("소모품이 추가되었습니다.")
                        binding.mainView.makeSnackBar("소모품이 추가되었습니다.")
                    }
                    is DataState.Error -> {
//                        makeToast("소모품이 정상적으로 추가되지않았습니다. 다시 한번 시도해주세요!.")
                        binding.mainView.makeSnackBar("소모품이 정상적으로 추가되지않았습니다. 다시 한번 시도해주세요!.")
                    }
                }
            })
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_categoryDetailFragment_pop)
        }

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    companion object {
        fun newInstance() = CategoryDetailFragment()

        const val ARGS_KEY = "categoryName"
    }
}