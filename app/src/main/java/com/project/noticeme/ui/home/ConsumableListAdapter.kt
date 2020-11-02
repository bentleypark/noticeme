package com.project.noticeme.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.databinding.MaterialItemBinding

class ConsumableListAdapter(private val list: List<ConsumableEntity>) :
    RecyclerView.Adapter<ConsumableListAdapter.MaterialListViewHolder>() {

    private lateinit var bindingItem: MaterialItemBinding

    inner class MaterialListViewHolder(private val binding: MaterialItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ConsumableEntity) {
            binding.tvTitle.text = item.title
            binding.ivMaterialImg.setImageResource(item.image)
//            binding.tvExpireTime.text = item.duration.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        bindingItem = MaterialItemBinding.inflate(layoutInflater)
        return MaterialListViewHolder(bindingItem)
    }

    override fun onBindViewHolder(holder: MaterialListViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount() = list.size
}