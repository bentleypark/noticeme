package com.project.noticeme.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.project.noticeme.R
import com.project.noticeme.common.ex.runLayoutAnimation
import com.project.noticeme.common.utils.preference.SharedPreferenceManager
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.data.state.DataState
import com.project.noticeme.databinding.FragmentHomeBinding
import com.project.noticeme.notification.JobSchedulerStart
import com.project.noticeme.ui.base.BaseFragment
import com.project.noticeme.ui.home.adapt.UserConsumableListAdapter
import com.project.noticeme.ui.home.utils.SwipeHelperCallback
import com.project.noticeme.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by viewModels()
    private var userConsumableList = mutableListOf<UserConsumableEntity>()
    private lateinit var listAdapter: UserConsumableListAdapter
    private val swipeHelperCallback = SwipeHelperCallback().apply {
        setClamp(420f)
    }

    @Inject
    lateinit var pref: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewLifecycleOwnerLiveData.observe(this, {
            setUpObserve(it)
            activity?.onBackPressedDispatcher?.addCallback(
                it,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        requireActivity().finish()
                    }
                })
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        MobileAds.initialize(activity)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserConsumableData()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpView() {

        val itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvList)

        listAdapter = UserConsumableListAdapter(
            userConsumableList,
            viewModel,
            requireContext(),
            swipeHelperCallback
        )
        val size = resources.getDimensionPixelSize(R.dimen.material_item_size)
        binding.rvList.apply {
            adapter = listAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(SpaceDecoration(size))
            setOnTouchListener { _, _ ->
                swipeHelperCallback.removePreviousClamp(this)
                false
            }
            runLayoutAnimation()
        }

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addConsumableFragment)
        }

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.btnSetting.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
        }
    }

    private fun setUpObserve(viewLifecycleOwner: LifecycleOwner) {
        viewModel.apply {
            consumableList.observe(
                viewLifecycleOwner, {
                    binding.apply {
                        rvList.visibility = View.GONE
                        emptyList.isVisible = false
                        progressCircular.isVisible = false
                    }

                    if (it != null) {
                        when (it) {
                            is DataState.Success<List<UserConsumableEntity>> -> {
                                binding.progressCircular.isVisible = false
                                if (it.data.isNotEmpty()) {
                                    lifecycleScope.launch {
                                        binding.rvList.isVisible = true
                                        listAdapter.addAll(it.data.sortedByDescending { item -> item.priority }
                                            .toMutableList())


                                        userConsumableList = it.data.toMutableList()
                                        if (!pref.getIsOldNotificationUpdated()) {
                                            updateOldNotification()
                                        }

                                    }
                                } else {
                                    binding.emptyList.isVisible = true
                                }
                            }

                            is DataState.Loading -> {
                                binding.apply {
                                    progressCircular.isVisible = true
                                }
                            }
                        }
                    }
                }
            )

            dataStateForUpdate.observe(
                viewLifecycleOwner,
                {
                    when (it) {
                        is DataState.Success<Boolean> -> {
                            if (it.data) {
                                lifecycleScope.launch {
                                    refresh()
                                }
                            }
                        }
                    }
                }
            )

            deleteResult.observe(
                viewLifecycleOwner,
                {
                    when (it) {
                        is DataState.Success<Boolean> -> {
                            if (it.data) {
                                if (listAdapter.itemCount == 0) {
                                    viewModel.getUserConsumableData()
                                }
                            }
                        }
                    }
                }
            )
        }
    }

    private fun updateOldNotification() {
        userConsumableList.forEach { item ->
            setUpNotification(item.startDate + item.duration, item.id)
        }
        pref.setOldNotificationUpdated(true)
    }

    private fun setUpNotification(expiredDate: Long, id: Int) {
        if (pref.getNotificationSetting()) {
            JobSchedulerStart.start(requireContext(), expiredDate, id)
        }
    }

    private fun refresh() {
        findNavController().navigate(R.id.action_homeFragment_self)
    }
}