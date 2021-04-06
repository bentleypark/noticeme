package com.project.noticeme.ui.home.adapt

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.project.noticeme.App
import com.project.noticeme.R
import com.project.noticeme.common.ex.getResourceId
import com.project.noticeme.common.utils.const.Const.DAY_MILLISECONDS
import com.project.noticeme.common.utils.date.TimeInMillis
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.databinding.ConsumableItemBinding
import com.project.noticeme.notification.JobSchedulerStart
import com.project.noticeme.ui.home.HomeFragmentDirections
import com.project.noticeme.ui.home.utils.SwipeHelperCallback
import com.project.noticeme.ui.home.viewmodel.HomeViewModel
import com.project.noticeme.ui.splash.initialdata.InitialConsumableData.fetchImageTitle
import timber.log.Timber
import kotlin.math.absoluteValue

class UserConsumableListAdapter(
    private val list: MutableList<UserConsumableEntity>,
    private val viewModel: HomeViewModel,
    private val context: Context,
    swipeHelperCallback: SwipeHelperCallback
) :
    RecyclerView.Adapter<UserConsumableListAdapter.UserConsumableListViewHolder>() {

    private lateinit var bindingItem: ConsumableItemBinding

    inner class UserConsumableListViewHolder(private val binding: ConsumableItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: UserConsumableEntity, position: Int) {
            binding.apply {
                tvTitle.text = item.title

                if (item.imageTitle.isEmpty()) {
                    ivMaterialImg.setImageResource(
                        ivMaterialImg.context.getResourceId(
                            fetchImageTitle(item.title)
                        )
                    )
                } else {
                    ivMaterialImg.setImageResource(ivMaterialImg.context.getResourceId(item.imageTitle))
                }

                val result = getExpiredDay(System.currentTimeMillis(), item.endDate)
                getExpiredDayForTextView(result, tvExpireTime)

                btnDelete.isEnabled = false
                btnReset.isEnabled = false

                btnDelete.setOnClickListener {
                    openDeleteDialog(context, position)
                }

                btnReset.setOnClickListener {
                    openResetDialog(context, position)
                }

                consumableItem.setOnClickListener {
                    it.findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToConsumableDetailFragment(item.title)
                    )
                }
            }
        }

        fun getBtnDelete(): View = binding.btnDelete

        fun getBtnReset(): View = binding.btnReset

        fun getSwipeView(): View = binding.swipeItem
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
        holder.bind(item, position)
    }

    override fun getItemCount() = list.size

    fun addAll(items: MutableList<UserConsumableEntity>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    private fun removeAt(position: Int) {
        viewModel.delete(list[position])
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    private fun updateAt(position: Int) {
        val item = list[position]
        val currentDate = System.currentTimeMillis()
        viewModel.update(
            UserConsumableEntity(
                item.id,
                item.title,
                item.imageTitle,
                item.category,
                item.duration,
                currentDate,
                currentDate + item.duration + DAY_MILLISECONDS,
                item.priority
            )
        )

        if (viewModel.checkIsNotificationSettingOn()) {
            JobSchedulerStart.start(
                context,
                TimeInMillis().getCurrentTimeMillis() + item.duration,
                item.id
            )
        }
    }

    private fun getExpiredDay(startDate: Long, endDate: Long): Long {
        Timber.d("$startDate")
        return (endDate - startDate) / DAY_MILLISECONDS
    }

    @SuppressLint("SetTextI18n")
    private fun getExpiredDayForTextView(result: Long, textView: TextView) {
        when {
            result > 0 -> {
                textView.setTextColor(
                    App.globalApplicationContext.resources.getColor(
                        R.color.black,
                        null
                    )
                )
                textView.text = "-${result}일"
            }
            result == 0.toLong() -> {
                textView.setTextColor(
                    App.globalApplicationContext.resources.getColor(
                        R.color.expired_day_color_red,
                        null
                    )
                )
                textView.text = "D-Day"
            }
            else -> {
                textView.setTextColor(
                    App.globalApplicationContext.resources.getColor(
                        R.color.expired_day_color_red,
                        null
                    )
                )
                textView.text = "+${result.absoluteValue}일"
            }
        }
    }

    private fun openDeleteDialog(context: Context, position: Int) {
        MaterialAlertDialogBuilder(
            context, R.style.AlertDialogTheme
        )
            .setTitle(context.getString(R.string.remove_dialog_title))
            .setPositiveButton(context.getString(R.string.btn_confirm_title)) { dialog, _ ->
                dialog.dismiss()
                removeAt(position)
            }
            .setNegativeButton(context.getString(R.string.btn_cancel_title)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun openResetDialog(context: Context, position: Int) {
        MaterialAlertDialogBuilder(
            context, R.style.AlertDialogTheme
        )
            .setTitle(context.getString(R.string.renewal_dialog_title))
            .setPositiveButton(context.getString(R.string.btn_confirm_title)) { dialog, _ ->
                dialog.dismiss()
                updateAt(position)
            }
            .setNegativeButton(context.getString(R.string.btn_cancel_title)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }
}