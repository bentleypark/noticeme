package com.project.noticeme.ui.category.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.project.noticeme.data.model.ConsumableCategory
import com.project.noticeme.ui.category.initialdata.ConsumableCategoryData

class CategoryViewModel @ViewModelInject
constructor(application: Application) : AndroidViewModel(application) {

    private val _categoryList = liveData {
        emit(ConsumableCategoryData.fetchData(application.baseContext))
    }

    val categoryList: LiveData<List<ConsumableCategory>>
        get() = _categoryList
}