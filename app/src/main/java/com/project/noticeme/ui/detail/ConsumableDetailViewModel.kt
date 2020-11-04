package com.project.noticeme.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import com.project.noticeme.common.base.BaseViewModel
import com.project.noticeme.data.repository.MainRepository

class ConsumableDetailViewModel @ViewModelInject
constructor(
    private val mainRepository: MainRepository
): BaseViewModel() {

}