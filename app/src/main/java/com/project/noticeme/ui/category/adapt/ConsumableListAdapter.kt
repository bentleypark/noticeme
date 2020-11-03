package com.project.noticeme.ui.category.adapt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.databinding.ConsumableItemBinding
import com.project.noticeme.ui.category.viewmodel.CategoryDetailViewModel
import kotlinx.android.extensions.LayoutContainer
import java.util.concurrent.TimeUnit

class ConsumableListAdapter(
    private val list: MutableList<ConsumableEntity>,
    private val viewmodel: CategoryDetailViewModel
) : RecyclerView.Adapter<ConsumableListAdapter.ConsumableListViewHolder>() {

    private lateinit var bindingItem: ConsumableItemBinding

    inner class ConsumableListViewHolder(private val binding: ConsumableItemBinding) :
        RecyclerView.ViewHolder(binding.root), LayoutContainer {

        override val containerView: View?
            get() = binding.root

        fun bind(item: ConsumableEntity) {
            binding.apply {
                tvTitle.text = item.title
                ivMaterialImg.setImageResource(item.image)
                tvExpireTime.text = getDurationWithDay(item.duration)
                consumableItem.setOnClickListener {
                    viewmodel.insert(
                        UserConsumableEntity(
                            item.title,
                            item.image,
                            item.duration,
                            System.currentTimeMillis(),
                            "없음"
                        )
                    )
                }
            }
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

    private fun getDurationWithDay(milliseconds: Long): String {
        return "${(milliseconds / TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS))}일"
    }
}