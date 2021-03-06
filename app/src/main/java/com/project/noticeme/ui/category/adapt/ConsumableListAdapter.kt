package com.project.noticeme.ui.category.adapt

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.databinding.ConsumableItemBinding
import com.project.noticeme.ui.category.viewmodel.CategoryDetailViewModel

class ConsumableListAdapter(
    private val list: MutableList<ConsumableEntity>,
    private val viewModel: CategoryDetailViewModel,
    private val context: Context
) : RecyclerView.Adapter<ConsumableListViewHolder>() {

    private lateinit var bindingItem: ConsumableItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsumableListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        bindingItem = ConsumableItemBinding.inflate(layoutInflater)
        return ConsumableListViewHolder(bindingItem, viewModel, context)
    }

    override fun onBindViewHolder(holder: ConsumableListViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount() = list.size
}
