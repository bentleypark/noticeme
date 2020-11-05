package com.project.noticeme.ui.home.adapt

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.project.noticeme.App
import com.project.noticeme.R
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.databinding.ConsumableItemBinding
import kotlinx.android.extensions.LayoutContainer
import java.util.concurrent.TimeUnit

class UserConsumableListAdapter(private val list: MutableList<UserConsumableEntity>) :
    RecyclerView.Adapter<UserConsumableListAdapter.UserConsumableListViewHolder>() {

    private lateinit var bindingItem: ConsumableItemBinding

    inner class UserConsumableListViewHolder(private val binding: ConsumableItemBinding) :
        RecyclerView.ViewHolder(binding.root), LayoutContainer {

        override val containerView: View?
            get() = binding.root

        @SuppressLint("SetTextI18n")
        fun bind(item: UserConsumableEntity) {
            binding.apply {
                tvTitle.text = item.title
                ivMaterialImg.setImageResource(item.image)
                val result = getExpiredDay(item.startDate, item.endDate)
                if (result > 0) {
                    tvExpireTime.text = "-${result}일"
                } else {
                    tvExpireTime.setTextColor(
                        App.globalApplicationContext.resources.getColor(
                            R.color.expired_day_color_red,
                            null
                        )
                    )
                    tvExpireTime.text = "+${result}일"
                }

                consumableItem.setOnClickListener {
                    val args = Bundle()
                    args.putString("item_title", item.title)
                    it.findNavController().navigate(
                        R.id.action_homeFragment_to_consumableDetailFragment, args
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserConsumableListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        bindingItem = ConsumableItemBinding.inflate(layoutInflater)
        return UserConsumableListViewHolder(bindingItem)
    }

    override fun onBindViewHolder(holder: UserConsumableListViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount() = list.size

    fun addAll(items: MutableList<UserConsumableEntity>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int): UserConsumableEntity {
        val item = list[position]
        list.removeAt(position)
        notifyItemRemoved(position)
        return item
    }

    private fun getExpiredDay(startDate: Long, endDate: Long): Long {
        return (endDate - startDate) / TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)
    }
}