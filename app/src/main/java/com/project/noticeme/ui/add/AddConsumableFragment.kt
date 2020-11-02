package com.project.noticeme.ui.add

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
import com.project.noticeme.databinding.FragmentAddComsumableBinding
import com.project.noticeme.ui.home.SpaceDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddConsumableFragment : Fragment(),
    ViewBindingHolder<FragmentAddComsumableBinding> by ViewBindingHolderImpl() {

    private val viewModel: AddConsumableViewModel by viewModels()
    private lateinit var listAdapter: ConsumableCategoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_addConsumableFragment_pop)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(FragmentAddComsumableBinding.inflate(layoutInflater), this) {
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
    }

    companion object {
        private var fragment: AddConsumableFragment? = null

        @JvmStatic
        fun newInstance() =
            fragment ?: synchronized(this) {
                fragment ?: AddConsumableFragment().also { fragment = it }
            }

        private const val SPAN_COUNT_PORTRAIT = 3
        private const val CATEGORY_COUNT = 5
    }
}