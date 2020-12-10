package com.project.noticeme.ui.setting

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.noticeme.R
import com.project.noticeme.common.base.ViewBindingHolder
import com.project.noticeme.common.base.ViewBindingHolderImpl
import com.project.noticeme.databinding.FragmentRuleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RuleFragment : Fragment(),
    ViewBindingHolder<FragmentRuleBinding> by ViewBindingHolderImpl() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentRuleBinding.inflate(layoutInflater), this) {
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val content = getText(R.string.personal_information_processing_policy).toString()
        binding!!.ruleContent.text = Html.fromHtml(content, HtmlCompat.FROM_HTML_MODE_LEGACY)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_ruleFragment_pop)
        }
    }
}