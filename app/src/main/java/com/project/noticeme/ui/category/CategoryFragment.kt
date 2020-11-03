package com.project.noticeme.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.project.noticeme.R
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.databinding.FragmentCategoryBinding
import com.project.noticeme.ui.category.adapt.ConsumableCategoryListAdapter
import com.project.noticeme.ui.category.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment(),
    ViewBindingHolder<FragmentCategoryBinding> by ViewBindingHolderImpl() {

    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var listAdapter: ConsumableCategoryListAdapter

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
    }

    companion object {
        private const val SPAN_COUNT_PORTRAIT = 3
    }
}