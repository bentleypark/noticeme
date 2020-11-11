package com.project.noticeme.ui.category.adapt

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.databinding.ConsumableItemBinding
import com.project.noticeme.ui.category.viewmodel.CategoryDetailViewModel
import kotlinx.android.extensions.LayoutContainer
import java.util.concurrent.TimeUnit

class ConsumableListViewHolder(private val binding: ConsumableItemBinding, private val viewModel: CategoryDetailViewModel) :
    RecyclerView.ViewHolder(binding.root), LayoutContainer {

    override val containerView: View?
        get() = binding.root

    fun bind(item: ConsumableEntity) {
        binding.apply {
            tvTitle.text = item.title
            ivMaterialImg.setImageResource(item.image)
            tvExpireTime.text = getDurationWithDay(item.duration)
            consumableItem.setOnClickListener {
                viewModel.insert(
                    UserConsumableEntity(
                        item.title,
                        item.image,
                        item.category,
                        item.duration,
                        System.currentTimeMillis(),
                        System.currentTimeMillis() + item.duration,
                        0
                    )
                )
            }
        }
    }

    private fun getDurationWithDay(milliseconds: Long): String {
        return "${(milliseconds / TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS))}일"
    }
}