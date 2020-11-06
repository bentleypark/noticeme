package com.project.noticeme.ui.detail

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.project.noticeme.R
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.common.ex.makeToast
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.data.state.DataState
import com.project.noticeme.databinding.FragmentConsumableDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ConsumableDetailFragment : Fragment(),
    ViewBindingHolder<FragmentConsumableDetailBinding> by ViewBindingHolderImpl() {

    private val viewModel: ConsumableDetailViewModel by viewModels()
    private lateinit var userConsumableItem: UserConsumableEntity
    var prioirty = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(FragmentConsumableDetailBinding.inflate(layoutInflater), this) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString("item_title")
        viewModel.getWithTitle(title!!)
        setView()
        setObserve()
    }

    private fun getDurationWithDay(milliseconds: Long): String {
        return "${(milliseconds / TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS))}"
    }


    private fun setView() {
        binding!!.apply {
            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_consumableDetailFragment_pop)
            }

            dataPicker.init(
                2020, 11, 5
            ) { view, year, monthOfYear, dayOfMonth ->

            }

            priorityBtnGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
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
                progressCircular.isVisible = true
                val duration = tvDuration.text.toString().toInt() * TimeUnit.MILLISECONDS.convert(
                    1,
                    TimeUnit.DAYS
                )
                val title = tvTitle.text.toString()
                viewModel.update(
                    UserConsumableEntity(
                        title,
                        userConsumableItem.image,
                        userConsumableItem.category,
                        duration,
                        userConsumableItem.startDate,
                        userConsumableItem.endDate,
                        prioirty
                    )
                )
            }

            tvDelete.setOnClickListener {
                openDeleteDialog()
            }
        }
    }

    private fun setObserve() {
        viewModel.userConsumableItem.observe(viewLifecycleOwner,
            {
                when (it) {
                    is DataState.Success<UserConsumableEntity> -> {
                        binding!!.apply {
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
                    }
                }
            }
        )

        viewModel.dataStateForUpdate.observe(viewLifecycleOwner,
            {
                when (it) {
                    is DataState.Success<Boolean> -> {
                        binding!!.apply {
                            progressCircular.isVisible = false
                        }

                        makeToast("소모품의 정보가 수정돠었습니다.")
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
                        makeToast("소모품이 삭제돠었습니다.")
                    }
                }
            }
        )
    }

    private fun openDeleteDialog() {
        MaterialAlertDialogBuilder(
            requireContext(), R.style.AlertDialogTheme
        )
            .setTitle("소모품을 삭제하시겠습니까?")
            .setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
                deleteItem()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun deleteItem() {
        binding!!.apply {
            viewModel.delete(
                UserConsumableEntity(
                    userConsumableItem.title,
                    userConsumableItem.image,
                    userConsumableItem.category,
                    tvDuration.text.toString().toInt() * TimeUnit.MILLISECONDS.convert(
                        1,
                        TimeUnit.DAYS
                    ),
                    userConsumableItem.startDate,
                    userConsumableItem.endDate,
                    prioirty
                )
            )
        }
    }

    companion object {
        fun newInstance() = ConsumableDetailFragment()
    }
}