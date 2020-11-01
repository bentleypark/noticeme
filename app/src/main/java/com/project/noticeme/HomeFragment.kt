package com.project.noticeme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),
    ViewBindingHolder<FragmentHomeBinding> by ViewBindingHolderImpl() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var listAdapter: ConsumableListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(FragmentHomeBinding.inflate(layoutInflater), this) {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.materialList.observe(
            viewLifecycleOwner, {

                listAdapter = ConsumableListAdapter(it)

                val size = resources.getDimensionPixelSize(R.dimen.material_item_size)
                binding!!.rvList.apply {
                    adapter = listAdapter
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    setHasFixedSize(true)
                    addItemDecoration(SpaceDecoration(size))
                }
            }
        )
    }
}