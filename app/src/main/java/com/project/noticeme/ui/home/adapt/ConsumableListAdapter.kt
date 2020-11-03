package com.project.noticeme.ui.home.adapt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.databinding.ConsumableItemBinding
import kotlinx.android.extensions.LayoutContainer
import java.util.concurrent.TimeUnit


class ConsumableListAdapter(
    private val list: MutableList<ConsumableEntity>
) :
    RecyclerView.Adapter<ConsumableListAdapter.ConsumableListViewHolder>() {

    private lateinit var bindingItem: ConsumableItemBinding

    inner class ConsumableListViewHolder(private val binding: ConsumableItemBinding) :
        RecyclerView.ViewHolder(binding.root), LayoutContainer {

        override val containerView: View?
            get() = binding.root

        fun bind(item: ConsumableEntity) {
            binding.tvTitle.text = item.title
            binding.ivMaterialImg.setImageResource(item.image)
            binding.tvExpireTime.text = getDurationWithDay(item.duration)
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

    private fun getDurationWithDay(milliseconds: Long): String {
        return "${(milliseconds / TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS))}일"
    }
}