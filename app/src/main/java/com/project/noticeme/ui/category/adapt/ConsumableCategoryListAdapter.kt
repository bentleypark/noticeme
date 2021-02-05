package com.project.noticeme.ui.category.adapt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.project.noticeme.data.model.ConsumableCategory
import com.project.noticeme.databinding.CategoryItemBinding
import com.project.noticeme.ui.category.CategoryFragmentDirections

class ConsumableCategoryListAdapter(private val list: List<ConsumableCategory>) :
    RecyclerView.Adapter<ConsumableCategoryListAdapter.ConsumableCategoryListViewHolder>() {

    private lateinit var bindingItem: CategoryItemBinding

    inner class ConsumableCategoryListViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ConsumableCategory) {
            binding.tvCategoryTitle.text = item.title
            binding.ivCategoryImg.setImageResource(item.image)
            binding.categoryItem.setOnClickListener {
                it.findNavController().navigate(
                    CategoryFragmentDirections.actionCategoryFragmentToCategoryDetailFragment(item.title)
                )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConsumableCategoryListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        bindingItem = CategoryItemBinding.inflate(layoutInflater)
        return ConsumableCategoryListViewHolder(bindingItem)
    }

    override fun onBindViewHolder(holder: ConsumableCategoryListViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount() = list.size
}