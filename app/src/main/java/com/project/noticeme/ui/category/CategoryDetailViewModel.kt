package com.project.noticeme.ui.category

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.noticeme.common.base.BaseViewModel
import com.project.noticeme.data.repository.MainRepository
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.state.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CategoryDetailViewModel @ViewModelInject
constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val _consumableList = MutableLiveData<DataState<List<ConsumableEntity>>>()

    val consumableList: LiveData<DataState<List<ConsumableEntity>>>
        get() = _consumableList

    fun findConsumableWithCategory(categoryName: String) {
        viewModelScope.launch {
            mainRepository.findConsumableWithCategory(categoryName)
                .onEach { consumableList ->
                    _consumableList.value = consumableList
                }
                .launchIn(viewModelScope)
        }
    }
}