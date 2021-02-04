package com.project.noticeme.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.project.noticeme.R
import com.project.noticeme.databinding.FragmentCategoryBinding
import com.project.noticeme.ui.base.BaseFragment
import com.project.noticeme.ui.category.adapt.ConsumableCategoryListAdapter
import com.project.noticeme.ui.category.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var listAdapter: ConsumableCategoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.categoryList.observe(viewLifecycleOwner,
            {
                listAdapter = ConsumableCategoryListAdapter(it)

                binding.categoryList.apply {
                    adapter = listAdapter
                    layoutManager = GridLayoutManager(context, SPAN_COUNT_PORTRAIT)
                    addItemDecoration(ItemDivideDecoration(ITEM_DIV_HEIGHT))
                    setHasFixedSize(true)
                }
            })

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_categoryFragment_pop)
            }

            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)

            btnAdd.setOnClickListener {
                findNavController().navigate(R.id.action_categoryFragment_to_addCustomConsumableFragment)
            }

            btnSearch.setOnClickListener {
                findNavController().navigate(R.id.action_categoryFragment_to_searchFragment)
            }
        }
    }

    companion object {
        private const val SPAN_COUNT_PORTRAIT = 3
        private const val ITEM_DIV_HEIGHT = 15
    }
}