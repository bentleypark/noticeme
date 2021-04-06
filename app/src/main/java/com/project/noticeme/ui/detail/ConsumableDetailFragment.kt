package com.project.noticeme.ui.detail

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.project.noticeme.R
import com.project.noticeme.common.ex.makeGone
import com.project.noticeme.common.ex.makeSnackBar
import com.project.noticeme.common.ex.makeVisible
import com.project.noticeme.common.ex.showKeyboard
import com.project.noticeme.common.utils.const.Const.DAY_MILLISECONDS
import com.project.noticeme.common.utils.preference.SharedPreferenceManager
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.data.state.DataState
import com.project.noticeme.databinding.FragmentConsumableDetailBinding
import com.project.noticeme.notification.JobSchedulerStart
import com.project.noticeme.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class ConsumableDetailFragment : BaseFragment<FragmentConsumableDetailBinding>() {

    private val viewModel: ConsumableDetailViewModel by viewModels()
    private lateinit var userConsumableItem: UserConsumableEntity
    private var prioirty = 0
    private var startDate: Long = 0
    private var duration: Long = 0

    @Inject
    lateinit var pref: SharedPreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConsumableDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString(ARGS_KEY)
        viewModel.getWithTitle(title!!)
        setView()
        setObserve()
    }

    private fun getDurationWithDay(milliseconds: Long): String {
        return "${(milliseconds / TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS))}"
    }


    private fun setView() {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_consumableDetailFragment_pop)
            }

            tvDuration.apply {
                requestFocus()
                showKeyboard()
            }

            priorityBtnGroup.addOnButtonCheckedListener { _, checkedId, _ ->
                when (checkedId) {
                    btnPriority0.id -> {
                        prioirty = 0
                    }

                    btnPriority1.id -> {
                        prioirty = 1
                    }

                    btnPriority2.id -> {
                        prioirty = 2
                    }

                    btnPriority3.id -> {
                        prioirty = 3
                    }
                }
            }

            tvConfirm.setOnClickListener {
                progressCircular.makeVisible()

                val durationStr = tvDuration.text.toString()
                if (durationStr.isNotEmpty() && durationStr.isDigitsOnly()) {
                    duration = durationStr.toInt() * DAY_MILLISECONDS

                    if (startDate == 0.toLong()) {
                        startDate = userConsumableItem.startDate
                    }

                    viewModel.update(
                        UserConsumableEntity(
                            userConsumableItem.id,
                            userConsumableItem.title,
                            userConsumableItem.imageTitle,
                            userConsumableItem.category,
                            duration,
                            startDate,
                            startDate + duration + DAY_MILLISECONDS,
                            prioirty
                        )
                    )

                    setUpNotification(userConsumableItem.id, startDate + duration)

                } else {
                    progressCircular.makeGone()
                    binding.mainView.makeSnackBar(resources.getString(R.string.detail_fragment_no_duration_msg))
                }
            }

            tvDelete.setOnClickListener {
                openDeleteDialog()
            }

            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
        }
    }

    private fun setObserve() {
        viewModel.userConsumableItem.observe(viewLifecycleOwner,
            {
                when (it) {
                    is DataState.Success<UserConsumableEntity> -> {
                        binding.apply {
                            progressCircular.isVisible = false
                        }

                        userConsumableItem = it.data
                        binding.apply {
                            tvTitle.text = userConsumableItem.title
                            tvDuration.text =
                                SpannableStringBuilder(getDurationWithDay(userConsumableItem.duration))
                        }
                        prioirty = userConsumableItem.priority
                        binding.apply {
                            when (prioirty) {
                                0 -> priorityBtnGroup.check(btnPriority0.id)
                                1 -> priorityBtnGroup.check(btnPriority1.id)
                                2 -> priorityBtnGroup.check(btnPriority2.id)
                                3 -> priorityBtnGroup.check(btnPriority3.id)
                            }
                        }
                        val calendar = Calendar.getInstance()
                        calendar.timeInMillis = userConsumableItem.startDate
                        binding.dataPicker.init(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ) { _, year, monthOfYear, dayOfMonth ->
                            Timber.d("dateChange()")
                            calendar.set(year, monthOfYear, dayOfMonth)
                            startDate = calendar.timeInMillis
                        }
                    }
                }
            }
        )

        viewModel.dataStateForUpdate.observe(viewLifecycleOwner,
            {
                when (it) {
                    is DataState.Success<Boolean> -> {
                        binding.apply {
                            progressCircular.isVisible = false
                        }

                        binding.mainView.makeSnackBar(getString(R.string.consumable_modification_msg))
                        findNavController().navigate(R.id.action_consumableDetailFragment_pop)
                    }
                }
            }
        )

        viewModel.deleteResult.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is DataState.Success<Boolean> -> {
                        findNavController().navigate(R.id.action_consumableDetailFragment_pop)
                        binding.mainView.makeSnackBar(getString(R.string.consumable_remove_msg))
                    }
                }
            }
        )
    }


    private fun openDeleteDialog() {
        MaterialAlertDialogBuilder(
            requireContext(), R.style.AlertDialogTheme
        )
            .setTitle(getString(R.string.remove_dialog_title))
            .setPositiveButton(getString(R.string.btn_confirm_title)) { dialog, _ ->
                dialog.dismiss()
                deleteItem()
            }
            .setNegativeButton(getString(R.string.btn_cancel_title)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun deleteItem() {
        binding.apply {
            viewModel.delete(
                UserConsumableEntity(
                    userConsumableItem.id,
                    userConsumableItem.title,
                    userConsumableItem.imageTitle,
                    userConsumableItem.category,
                    tvDuration.text.toString().toInt() * DAY_MILLISECONDS,
                    userConsumableItem.startDate,
                    userConsumableItem.endDate,
                    prioirty
                )
            )
        }
    }

    private fun setUpNotification(id: Int, expiredDate: Long) {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.clear(Calendar.HOUR_OF_DAY)
        calendar.clear(Calendar.HOUR)
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)

        if (pref.getNotificationSetting()) {
            JobSchedulerStart.start(
                requireContext(),
                expiredDate,
                id
            )
        }
    }

    companion object {
        const val ARGS_KEY = "itemTitle"
    }
}