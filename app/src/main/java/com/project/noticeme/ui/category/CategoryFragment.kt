package com.project.noticeme.ui.category

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.project.noticeme.R
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.state.DataState
import com.project.noticeme.databinding.FragmentCategoryBinding
import com.project.noticeme.ui.category.adapt.ConsumableCategoryListAdapter
import com.project.noticeme.ui.category.adapt.ConsumableListAdapter
import com.project.noticeme.ui.category.viewmodel.CategoryDetailViewModel
import com.project.noticeme.ui.category.viewmodel.CategoryViewModel
import com.project.noticeme.ui.home.SpaceDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment(),
    ViewBindingHolder<FragmentCategoryBinding> by ViewBindingHolderImpl() {

    private val viewModel: CategoryViewModel by viewModels()
    private val detailViewModel: CategoryDetailViewModel by viewModels()
    private lateinit var listAdapter: ConsumableCategoryListAdapter
    private lateinit var searchAdapter: ConsumableListAdapter

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        requireActivity().onBackPressedDispatcher.addCallback(this) {
//            findNavController().navigate(R.id.action_categoryFragment_pop)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(FragmentCategoryBinding.inflate(layoutInflater), this) {
        MobileAds.initialize(activity) {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.categoryList.observe(viewLifecycleOwner,
            {
                listAdapter = ConsumableCategoryListAdapter(it)

                binding!!.categoryList.apply {
                    adapter = listAdapter
                    layoutManager = GridLayoutManager(context, SPAN_COUNT_PORTRAIT)
                    addItemDecoration(ItemDivideDecoration(15))
                    setHasFixedSize(true)
                }
            })

        binding!!.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_pop)
        }

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_to_addCustomConsumableFragment)
        }

//        binding.etSearch.setOnFocusChangeListener { _, hasFocus ->
//            binding.transparentView.isGone = !hasFocus
//        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                lifecycleScope.launch {
                    delay(1000)
                    val inputText = s.toString().trim()
                    viewModel.searchWithTitle(inputText)
                }
            }
        })

        viewModel.searchList.observe(
            viewLifecycleOwner, {
                when (it) {
                    is DataState.Success<List<ConsumableEntity>> -> {
                        binding.apply {
                            rv_list.isGone = true
                            searchList.isGone = true
                            btnAdd.isGone = true

                            if (it.data.isEmpty()) {
                                btnAdd.isGone = false
                            } else {
                                searchList.isGone = false
                            }

                            searchAdapter =
                                ConsumableListAdapter(it.data.toMutableList(), detailViewModel)
                            val size = resources.getDimensionPixelSize(R.dimen.material_item_size)
                            searchList.apply {
                                adapter = listAdapter
                                layoutManager =
                                    LinearLayoutManager(
                                        context,
                                        LinearLayoutManager.VERTICAL,
                                        false
                                    )
                                setHasFixedSize(true)
                                addItemDecoration(SpaceDecoration(size))
                            }
                        }
                    }
                }
            }
        )
    }

    companion object {
        private const val SPAN_COUNT_PORTRAIT = 3
    }
}