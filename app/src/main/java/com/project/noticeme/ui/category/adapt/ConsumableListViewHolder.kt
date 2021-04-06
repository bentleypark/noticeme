package com.project.noticeme.ui.category.adapt

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.project.noticeme.R
import com.project.noticeme.common.ex.getResourceId
import com.project.noticeme.common.ex.makeToast
import com.project.noticeme.common.utils.const.Const.DAY_MILLISECONDS
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.databinding.ConsumableItemBinding
import com.project.noticeme.notification.JobSchedulerStart
import com.project.noticeme.ui.category.viewmodel.CategoryDetailViewModel
import com.project.noticeme.ui.splash.initialdata.InitialConsumableData
import java.util.concurrent.TimeUnit

class ConsumableListViewHolder(
    private val binding: ConsumableItemBinding,
    private val viewModel: CategoryDetailViewModel,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ConsumableEntity) {
        binding.apply {
            tvTitle.text = item.title

            if (item.imageTitle.isEmpty()) {
                ivMaterialImg.setImageResource(
                    ivMaterialImg.context.getResourceId(
                        InitialConsumableData.fetchImageTitle(item.title)
                    )
                )
            } else {
                ivMaterialImg.setImageResource(ivMaterialImg.context.getResourceId(item.imageTitle))
            }

            tvExpireTime.text = getDurationWithDay(item.duration)

            consumableItem.setOnClickListener {
                if (!viewModel.checkIfItemIsAlreadyInserted(item.title)) {
                    if (viewModel.checkIsNotificationSettingOn()) {
                        JobSchedulerStart.start(
                            context,
                            viewModel.getCurrentTimeMillis() + item.duration,
                            item.id
                        )
                    }
                    viewModel.insert(
                        UserConsumableEntity(
                            item.id,
                            item.title,
                            item.imageTitle,
                            item.category,
                            item.duration,
                            viewModel.getCurrentTimeMillis(),
                            viewModel.getCurrentTimeMillis() + item.duration + DAY_MILLISECONDS,
                            0
                        )
                    )
                } else {
                    context.makeToast(context.getString(R.string.consumable_add_warning_msg))
                }
            }
        }
    }

    private fun getDurationWithDay(milliseconds: Long): String {
        return "${
            (milliseconds / TimeUnit.MILLISECONDS.convert(
                1,
                TimeUnit.DAYS
            ))
        }${context.getString(R.string.tv_day_title)}"
    }
}