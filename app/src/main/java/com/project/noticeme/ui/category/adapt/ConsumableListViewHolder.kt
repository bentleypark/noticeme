package com.project.noticeme.ui.category.adapt

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.project.noticeme.R
import com.project.noticeme.common.ex.makeToast
import com.project.noticeme.common.utils.const.Const.DAY_MILLISECONDS
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.databinding.ConsumableItemBinding
import com.project.noticeme.notification.JobSchedulerStart
import com.project.noticeme.ui.category.viewmodel.CategoryDetailViewModel
import kotlinx.android.extensions.LayoutContainer
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ConsumableListViewHolder(
    private val binding: ConsumableItemBinding,
    private val viewModel: CategoryDetailViewModel,
    private val context: Context
) :
    RecyclerView.ViewHolder(binding.root), LayoutContainer {

    override val containerView: View
        get() = binding.root

    fun bind(item: ConsumableEntity) {
        binding.apply {
            tvTitle.text = item.title
            ivMaterialImg.setImageResource(item.image)
            tvExpireTime.text = getDurationWithDay(item.duration)


            val calendar = Calendar.getInstance()
            calendar.time = Date()
            calendar.clear(Calendar.HOUR_OF_DAY)
            calendar.clear(Calendar.HOUR)
            calendar.clear(Calendar.MINUTE)
            calendar.clear(Calendar.SECOND)
            calendar.clear(Calendar.MILLISECOND)

            consumableItem.setOnClickListener {

                if (!viewModel.checkIfItemIsAlreadyInserted(item.title)) {
                    if (viewModel.checkIsNotificationSettingOn()) {
                        JobSchedulerStart.start(context,  item.duration, item.id)
                    }
                    viewModel.insert(
                        UserConsumableEntity(
                            item.id,
                            item.title,
                            item.image,
                            item.category,
                            item.duration,
                            calendar.timeInMillis,
                            calendar.timeInMillis + item.duration + DAY_MILLISECONDS,
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