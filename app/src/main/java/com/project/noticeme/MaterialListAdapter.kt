package com.project.noticeme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.noticeme.data.model.Material
import com.project.noticeme.databinding.MaterialItemBinding

class MaterialListAdapter(private val list: List<Material>) :
    RecyclerView.Adapter<MaterialListAdapter.MaterialListViewHolder>() {

    private lateinit var bindingItem: MaterialItemBinding

    inner class MaterialListViewHolder(private val binding: MaterialItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Material) {
            binding.tvTitle.text = item.title
            binding.tvExpireTime.text = item.expireDate
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