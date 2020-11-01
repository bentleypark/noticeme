package com.project.noticeme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.noticeme.data.model.Consumable
import com.project.noticeme.databinding.MaterialItemBinding

class MaterialListAdapter(private val list: List<Consumable>) :
    RecyclerView.Adapter<MaterialListAdapter.MaterialListViewHolder>() {

    private lateinit var bindingItem: MaterialItemBinding

    inner class MaterialListViewHolder(private val binding: MaterialItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Consumable) {
            binding.tvTitle.text = item.title
            binding.tvExpireTime.text = item.duration
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