package com.project.noticeme.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.databinding.ConsumableItemBinding


class ConsumableListAdapter(private val list: MutableList<ConsumableEntity>) :
    RecyclerView.Adapter<ConsumableListAdapter.ConsumableListViewHolder>() {

    private lateinit var bindingItem: ConsumableItemBinding

    inner class ConsumableListViewHolder(private val binding: ConsumableItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ConsumableEntity) {
            binding.tvTitle.text = item.title
            binding.ivMaterialImg.setImageResource(item.image)
//            binding.tvExpireTime.text = item.duration.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsumableListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        bindingItem = ConsumableItemBinding.inflate(layoutInflater)
        return ConsumableListViewHolder(bindingItem)
    }

    override fun onBindViewHolder(holder: ConsumableListViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount() = list.size

    fun addAll(items: MutableList<ConsumableEntity>) {
        list.addAll(items)
        notifyDataSetChanged()
    }
}