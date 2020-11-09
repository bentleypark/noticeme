package com.project.noticeme.ui.add

import androidx.hilt.lifecycle.ViewModelInject
import com.project.noticeme.common.base.BaseViewModel
import com.project.noticeme.data.repository.MainRepository

class AddCustomConsumableViewModel @ViewModelInject
constructor(
    private val mainRepository: MainRepository
): BaseViewModel() {

}