package com.project.noticeme.ui.add

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.project.noticeme.common.base.BaseViewModel
import com.project.noticeme.data.model.ConsumableCategory

class AddConsumableViewModel @ViewModelInject
constructor() : BaseViewModel() {

    private val _categoryList = liveData {
        emit(ConsumableCategoryData.fetchData())
    }

    val categoryList: LiveData<List<ConsumableCategory>>
        get() = _categoryList
}