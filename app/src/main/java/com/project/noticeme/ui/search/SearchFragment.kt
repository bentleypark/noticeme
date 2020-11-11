package com.project.noticeme.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.noticeme.R
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.common.ex.hideKeyboard
import com.project.noticeme.common.ex.makeGone
import com.project.noticeme.common.ex.makeVisible
import com.project.noticeme.common.ex.showKeyboard
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.room.SearchHistoryEntity
import com.project.noticeme.data.state.DataState
import com.project.noticeme.databinding.FragmentSearchBinding
import com.project.noticeme.ui.category.adapt.ConsumableListAdapter
import com.project.noticeme.ui.category.viewmodel.CategoryDetailViewModel
import com.project.noticeme.ui.detail.ConsumableDetailViewModel
import com.project.noticeme.ui.home.SpaceDecoration
import com.project.noticeme.ui.search.history.SearchHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : Fragment(),
    ViewBindingHolder<FragmentSearchBinding> by ViewBindingHolderImpl() {

    private val viewModel: SearchViewModel by viewModels()
    private val detailViewModel: CategoryDetailViewModel by viewModels()
    private lateinit var searchAdapter: ConsumableListAdapter
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(FragmentSearchBinding.inflate(layoutInflater), this) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.etSearch.apply {
            requestFocus()
            showKeyboard()
        }

        binding.btnBack.setOnClickListener {
            binding.etSearch.apply {
                clearFocus()
                hideKeyboard()
            }
            findNavController().navigate(R.id.action_searchFragment_pop)
        }

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_addCustomConsumableFragment)
        }

        searchAdapter = ConsumableListAdapter(mutableListOf(), detailViewModel)
        binding.searchList.apply {
            layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            setHasFixedSize(true)
            addItemDecoration(SpaceDecoration(resources.getDimensionPixelSize(R.dimen.material_item_size)))
        }


        searchHistoryAdapter =
            SearchHistoryAdapter(
                mutableListOf(
                    SearchHistoryEntity(0, "11.11", "칫솔"),
                    SearchHistoryEntity(0, "11.11", "칫솔")
                )
            )
        binding.rvSearchHistory.apply {
            adapter = searchHistoryAdapter
            layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            setHasFixedSize(true)
            addItemDecoration(SpaceDecoration(resources.getDimensionPixelSize(R.dimen.material_item_size)))
        }

        binding.etSearch.setOnFocusChangeListener { _, hasFocus ->
            binding.searchHistoryLayout.isGone = !hasFocus
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                lifecycleScope.launch {
                    delay(1000)
                    val inputText = s.toString().trim()
                    if (inputText.isNotEmpty()) {
                        viewModel.searchWithTitle(inputText)
                        binding.searchHistoryLayout.makeGone()
                    } else {
                        binding.apply {
                            searchHistoryLayout.makeVisible()
                            tvGuideMsg.makeGone()
                            tvSearchResultEmpty.makeGone()
                            btnAdd.makeGone()

                            etSearch.apply {
                                requestFocus()
                                showKeyboard()
                            }
                        }
                        if (searchAdapter.itemCount != 0) {
                            searchAdapter.clear()
                        }
                    }
                }
            }
        })

        viewModel.searchList.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is DataState.Success<List<ConsumableEntity>> -> {
                        binding.apply {
                            searchList.makeGone()
                            progressCircular.makeGone()
                        }

                        if (it.data.isNotEmpty()) {
                            binding.progressCircular.makeGone()
                            binding.searchList.makeVisible()
                            searchAdapter.addAll(it.data)
                        } else {
                            binding.apply {
                                tvSearchResultEmpty.makeVisible()
                                tvGuideMsg.makeVisible()
                                btnAdd.makeVisible()
                                etSearch.apply {
                                    clearFocus()
                                    hideKeyboard()
                                }
                            }
                        }
                    }

                    is DataState.Error -> {
                        Timber.d("Error")
                    }

                    is DataState.Loading -> {
                        binding.progressCircular.makeVisible()
                        binding.searchHistoryLayout.makeGone()
                    }
                }
            }
        )
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}