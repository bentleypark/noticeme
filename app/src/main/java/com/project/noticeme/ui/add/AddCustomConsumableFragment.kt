package com.project.noticeme.ui.add

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.project.noticeme.R
import com.project.noticeme.common.ex.hideKeyboard
import com.project.noticeme.common.ex.makeSnackBar
import com.project.noticeme.common.ex.makeToast
import com.project.noticeme.common.utils.const.Const.DAY_MILLISECONDS
import com.project.noticeme.common.utils.date.TimeInMillis
import com.project.noticeme.common.utils.preference.SharedPreferenceManager
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.data.state.DataState
import com.project.noticeme.databinding.FragmentAddCustomConsumableBinding
import com.project.noticeme.notification.JobSchedulerStart
import com.project.noticeme.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class AddCustomConsumableFragment : BaseFragment<FragmentAddCustomConsumableBinding>(),
    DatePicker.OnDateChangedListener {

    private val viewModel: AddCustomConsumableViewModel by viewModels()
    private val randomIcon = listOf(
        R.drawable.ic_random_icon_1_alien,
        R.drawable.ic_random_icon_2_cake,
        R.drawable.ic_random_icon_3_donut,
        R.drawable.ic_random_icon_4_flower_1,
        R.drawable.ic_random_icon_5_alien_2,
        R.drawable.ic_random_icon_6_flower_2,
        R.drawable.ic_random_icon_7_flower_3,
        R.drawable.ic_random_icon_8_smile,
        R.drawable.ic_random_icon_9_heart,
        R.drawable.ic_random_icon_10_planet
    )
    var prioirty = 0
    var startDate: Long = 0
    var duration: Long = 0

    @Inject
    lateinit var pref: SharedPreferenceManager

    @Inject
    lateinit var currentTimeMillis: TimeInMillis

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_addCustomConsumableFragment_pop)
                }
            })

        binding = FragmentAddCustomConsumableBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_addCustomConsumableFragment_pop)
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
                if (tvDuration.text.isNotEmpty() && tvTitle.text.isNotEmpty()) {
                    insertNewUserConsumable()
                } else {
                    mainView.makeSnackBar(getString(R.string.consumable_add_warning_msg2))
                }
            }

            val calendar = Calendar.getInstance()
            calendar.time = Date()
            calendar.clear(Calendar.HOUR_OF_DAY)
            calendar.clear(Calendar.HOUR)
            calendar.clear(Calendar.MINUTE)
            calendar.clear(Calendar.SECOND)
            calendar.clear(Calendar.MILLISECOND)
            dataPicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ) { _, year, monthOfYear, dayOfMonth ->
                calendar.set(year, monthOfYear, dayOfMonth)

                startDate = calendar.timeInMillis
            }
        }

        viewModel.dataStateUserConsumable.observe(viewLifecycleOwner,
            {
                when (it) {
                    is DataState.Success<Boolean> -> {
                        viewModel.insert(
                            ConsumableEntity(
                                0,
                                SpannableStringBuilder(binding.tvTitle.text).toString(),
                                randomIcon.random(),
                                getString(R.string.personal_category_title),
                                binding.tvDuration.text.toString()
                                    .toInt() * TimeUnit.MILLISECONDS.convert(
                                    1,
                                    TimeUnit.DAYS
                                )
                            )
                        )
                    }
                    is DataState.Error -> {
                        makeToast(getString(R.string.consumable_add_fail_msg))
                    }
                    is DataState.Loading -> {
                        lifecycleScope.launch {
                            binding.apply {
                                progressCircular.isVisible = true
                            }
                        }
                    }
                }
            }
        )

        viewModel.dataState.observe(viewLifecycleOwner,
            {
                when (it) {
                    is DataState.Success<Boolean> -> {
                        binding.apply {
                            progressCircular.isVisible = false
                            tvTitle.hideKeyboard()
                            tvDuration.hideKeyboard()
                        }
                        lifecycleScope.launch {

                            setUpNotification()

                            binding.mainView.makeSnackBar(getString(R.string.consumable_add_success_msg))
                            delay(1500)
                            findNavController().navigate(R.id.action_addCustomConsumableFragment_to_homeFragment)
                        }
                    }

                    else -> {}
                }
            })

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun insertNewUserConsumable() {
        duration = binding.tvDuration.text.toString().toInt() * TimeUnit.MILLISECONDS.convert(
            1,
            TimeUnit.DAYS
        )

        if (startDate == 0.toLong()) {
            startDate = System.currentTimeMillis()
        }

        viewModel.insertUserConsumable(
            UserConsumableEntity(
                0,
                SpannableStringBuilder(binding.tvTitle.text).toString(),
                randomIcon.random(),
                getString(R.string.personal_category_title),
                duration,
                startDate,
                startDate + duration + DAY_MILLISECONDS,
                prioirty
            )
        )
    }

    private fun setUpNotification() {
        if (pref.getNotificationSetting()) {
            JobSchedulerStart.start(
                requireContext(),
                currentTimeMillis.getCurrentTimeMillis() + duration,
                viewModel.getLastItemId()!! + 1
            )
        }
    }

    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val cal = Calendar.getInstance()
        cal.set(year, monthOfYear, dayOfMonth)
        startDate = cal.timeInMillis
    }
}