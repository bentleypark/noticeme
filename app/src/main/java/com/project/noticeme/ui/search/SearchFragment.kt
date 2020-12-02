package com.project.noticeme.ui.search

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.project.noticeme.R
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.common.ex.*
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.room.SearchHistoryEntity
import com.project.noticeme.data.state.DataState
import com.project.noticeme.databinding.FragmentSearchBinding
import com.project.noticeme.ui.category.adapt.ConsumableListAdapter
import com.project.noticeme.ui.category.viewmodel.CategoryDetailViewModel
import com.project.noticeme.ui.home.SpaceDecoration
import com.project.noticeme.ui.search.history.SearchHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

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
    ): View = initBinding(FragmentSearchBinding.inflate(layoutInflater), this) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.btnBack.setOnClickListener {
            binding.etSearch.apply {
                clearFocus()
                hideKeyboard()
            }
            findNavController().navigate(R.id.action_searchFragment_pop)
        }

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_addCustomConsumableFragment)
        }

        searchHistoryAdapter =
            SearchHistoryAdapter(
                mutableListOf(), viewModel
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

//        binding.etSearch.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                lifecycleScope.launch {
//                    delay(1000)
//                    val inputText = s.toString().trim()
//                    if (inputText.isNotEmpty()) {
//                        viewModel.searchWithTitle(inputText)
//                        binding.searchHistoryLayout.makeGone()
//                    } else {
//                        binding.apply {
//                            searchHistoryLayout.makeVisible()
//                            tvGuideMsg.makeGone()
//                            tvSearchResultEmpty.makeGone()
//                            btnAdd.makeGone()
//
//                            etSearch.apply {
//                                requestFocus()
//                                showKeyboard()
//                            }
//                        }
//                        if (searchAdapter.itemCount != 0) {
//                            searchAdapter.clear()
//                        }
//                    }
//                }
//            }
//        })

        binding.btnSearch.setOnClickListener {
            search()
        }

        binding.btnDeleteAll.setOnClickListener {
            searchHistoryAdapter.clear()
        }

        viewModel.searchList.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is DataState.Success<List<ConsumableEntity>> -> {
                        binding.apply {
                            progressCircular.makeGone()
                            tvSearchResultEmpty.makeGone()
                            tvGuideMsg.makeGone()
                            btnAdd.makeGone()
                            adView.makeGone()
                        }

                        if (it.data.isNotEmpty()) {
                            binding.progressCircular.makeGone()
                            binding.searchList.makeVisible()
                            binding.adView.makeVisible()
                            binding.apply {
                                etSearch.apply {
                                    clearFocus()
                                    hideKeyboard()
                                }
                            }
                        } else {
                            binding.apply {
                                tvSearchResultEmpty.makeVisible()
                                tvGuideMsg.makeVisible()
                                btnAdd.makeVisible()
                                binding.adView.makeVisible()
                                etSearch.apply {
                                    clearFocus()
                                    hideKeyboard()
                                }
                            }
                        }

                        searchAdapter =
                            ConsumableListAdapter(it.data.toMutableList(), detailViewModel, requireContext())
                        binding.searchList.apply {
                            adapter = searchAdapter
                            layoutManager =
                                LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                            setHasFixedSize(true)
                            addItemDecoration(SpaceDecoration(resources.getDimensionPixelSize(R.dimen.material_item_size)))
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

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        viewModel.searchHistoryList.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is DataState.Success<List<SearchHistoryEntity>> -> {
                        searchHistoryAdapter.addAll(it.data)
                    }
                }
            }
        )

        viewModel.selectedHistoryTitle.observe(
            viewLifecycleOwner,
            {
                searchWithHistory(it)
            }
        )

        detailViewModel.dataState.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Success<Boolean> -> {
                    makeToast("소모품이 추가되었습니다.")
                }
                is DataState.Error -> {
                    makeToast("소모품이 정상적으로 추가되지않았습니다. 다시 한번 시도해주세요!.")
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        binding!!.etSearch.apply {
            requestFocus()
            showKeyboard()

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search()
                }
                true
            }

            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.apply {
                        lifecycleScope.launch {
                            viewModel.getSearchHistory()
                            delay(1000)
                            tvSearchResultEmpty.makeGone()
                            tvGuideMsg.makeGone()
                            btnAdd.makeGone()
                            searchHistoryLayout.makeVisible()
                            searchList.makeGone()
                            adView.makeGone()
                        }
                    }
                }
            }
        }
    }

    private fun search() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val currentDate =
            "${calendar.get(Calendar.MONTH) + 1}.${calendar.get(Calendar.DAY_OF_MONTH)}"

        lifecycleScope.launch {
            delay(1000)
            val inputText = SpannableStringBuilder(binding!!.etSearch.text).toString().trim()
            if (inputText.isNotEmpty()) {
                viewModel.searchWithTitle(inputText)
                val historyItem = SearchHistoryEntity(0, currentDate, inputText)
                searchHistoryAdapter.add(historyItem)
                viewModel.insertSearchHistory(historyItem)
                binding.searchHistoryLayout.makeGone()
            }
        }
    }

    private fun searchWithHistory(title: String) {
        lifecycleScope.launch {
            viewModel.searchWithTitle(title)
            binding!!.searchHistoryLayout.makeGone()
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}