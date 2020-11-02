package com.project.noticeme.ui.add

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.noticeme.R

class AddComsumableFragment : Fragment() {

    companion object {
        fun newInstance() = AddComsumableFragment()
    }

    private lateinit var viewModel: AddComsumableViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_comsumable_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddComsumableViewModel::class.java)
        // TODO: Use the ViewModel
    }

}