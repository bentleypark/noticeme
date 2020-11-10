package com.project.noticeme.ui.category.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.project.noticeme.common.base.BaseViewModel
import com.project.noticeme.data.model.ConsumableCategory
import com.project.noticeme.data.repository.MainRepository
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.state.DataState
import com.project.noticeme.ui.category.initialdata.ConsumableCategoryData
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CategoryViewModel @ViewModelInject
constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val _categoryList = liveData {
        emit(ConsumableCategoryData.fetchData())
    }

    val categoryList: LiveData<List<ConsumableCategory>>
        get() = _categoryList


    private val _searchList = MutableLiveData<DataState<List<ConsumableEntity>>>()

    val searchList: LiveData<DataState<List<ConsumableEntity>>>
        get() = _searchList

    fun searchWithTitle(title: String) {
        viewModelScope.launch {
            mainRepository.findConsumableWithTitle(title)
                .onEach { dataState ->
                    _searchList.value = dataState
                }
        }
    }
}