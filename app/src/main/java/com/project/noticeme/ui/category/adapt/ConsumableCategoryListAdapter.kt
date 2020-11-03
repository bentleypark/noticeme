package com.project.noticeme.ui.category.adapt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.project.noticeme.R
import com.project.noticeme.data.model.ConsumableCategory
import com.project.noticeme.databinding.CategoryItemBinding
import com.project.noticeme.ui.category.CategoryFragmentDirections
import kotlinx.android.extensions.LayoutContainer

class ConsumableCategoryListAdapter(private val list: List<ConsumableCategory>) :
    RecyclerView.Adapter<ConsumableCategoryListAdapter.ConsumableCategoryListViewHolder>() {

    private lateinit var bindingItem: CategoryItemBinding

    inner class ConsumableCategoryListViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root), LayoutContainer {
        fun bind(item: ConsumableCategory) {
            binding.tvCategoryTitle.text = item.title
            binding.ivCategoryImg.setImageResource(item.image)
            binding.categoryItem.setOnClickListener {
                val args = Bundle()
                args.putString("category_name", item.title)
                it.findNavController().navigate(
                    R.id.action_addConsumableFragment_to_categoryDetailFragment, args
                )
            }
        }

        override val containerView: View?
            get() = binding.root
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