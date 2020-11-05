package com.project.noticeme.ui.detail

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.data.state.DataState
import com.project.noticeme.databinding.FragmentConsumableDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ConsumableDetailFragment : Fragment(),
    ViewBindingHolder<FragmentConsumableDetailBinding> by ViewBindingHolderImpl() {

    private val viewModel: ConsumableDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(FragmentConsumableDetailBinding.inflate(layoutInflater), this) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("item_title")
        viewModel.getwithTitle(title!!)

        viewModel.userConsumableItem.observe(viewLifecycleOwner,
            {
                when (it) {
                    is DataState.Success<UserConsumableEntity> -> {
                        binding!!.apply {
                            progressCircular.isVisible = false
                        }

                        val item = it.data
                        binding.apply {
                            tvTitle.text = item.title
                            tvDuration.text =
                                SpannableStringBuilder(getDurationWithDay(item.duration))
                        }
                    }
                }
            }
        )
    }

    private fun getDurationWithDay(milliseconds: Long): String {
        return "${(milliseconds / TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS))}"
    }

    companion object {
        fun newInstance() = ConsumableDetailFragment()
    }
}