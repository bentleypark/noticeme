package com.project.noticeme.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.project.noticeme.R
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.common.ex.launchActivityWithFinish
import com.project.noticeme.common.utils.preference.SharedPreferenceManager
import com.project.noticeme.databinding.FragmentSettingBinding
import com.project.noticeme.ui.MainActivity
import com.project.noticeme.ui.guide.GuideActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : Fragment(),
    ViewBindingHolder<FragmentSettingBinding> by ViewBindingHolderImpl() {

    @Inject
    lateinit var pref: SharedPreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentSettingBinding.inflate(layoutInflater), this) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSettings()
    }

    private fun setupSettings() {
        binding!!.apply {
            swNotificationSetting.isChecked = pref.getNotificationSetting()

            swNotificationSetting.setOnCheckedChangeListener { _, isChecked ->
                swNotificationSetting.isChecked = isChecked
                pref.setNotificationSetting(isChecked)
            }

            tvVersionInfo.text = requireActivity().packageManager.getPackageInfo(
                requireActivity().packageName,
                0
            ).versionName


            ivArrow1.setOnClickListener {
                findNavController().navigate(R.id.action_settingFragment_to_ruleFragment)
            }

            ivArrow2.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.email_address))
                requireContext().startActivity(intent)
            }

            ivArrow3.setOnClickListener {
                requireActivity().launchActivityWithFinish<GuideActivity>()
            }

            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_settingActivity_pop)
            }

            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
        }
    }

//    private fun initReview() {
////        val manager = ReviewManagerFactory.create(requireContext())
////        manager.requestReviewFlow().addOnCompleteListener { request ->
////            if (request.isSuccessful) {
////                val reviewInfo = request.result
////                manager.launchReviewFlow(requireActivity(), reviewInfo).addOnFailureListener {
////                    Timber.i("In-app review request failed, reason=$it")
////                }.addOnCompleteListener {
////                    Timber.i("In-app review finished")
////                }
////            } else {
////                Timber.e("In-app review request failed, reason=${request.exception}")
////            }
////        }
//
//        manager = ReviewManagerFactory.create(requireContext())
//        val request = manager.requestReviewFlow()
//        request.addOnCompleteListener { request ->
//            if (request.isSuccessful) {
//                // We got the ReviewInfo object
//                reviewInfo = request.result
//                manager.launchReviewFlow(requireActivity(), reviewInfo!!)
//                Timber.i("In-app review finished")
//            } else {
//                // There was some problem, continue regardless of the result.
//                Timber.e("In-app review request failed, reason=${request.exception}")
//
//            }
//        }
//    }
}