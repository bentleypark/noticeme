package com.project.noticeme.ui.category.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.noticeme.common.base.BaseViewModel
import com.project.noticeme.data.repository.MainRepository
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.data.state.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class CategoryDetailViewModel @ViewModelInject
constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val _consumableList = MutableLiveData<DataState<List<ConsumableEntity>>>()

    val consumableList: LiveData<DataState<List<ConsumableEntity>>>
        get() = _consumableList

    private val _dataState = MutableLiveData<DataState<Boolean>>()

    val dataState: LiveData<DataState<Boolean>>
        get() = _dataState

    fun findConsumableWithCategory(categoryName: String) {
        viewModelScope.launch {
            mainRepository.findConsumableWithCategory(categoryName)
                .onEach { consumableList ->
                    _consumableList.value = consumableList
                }
                .launchIn(viewModelScope)
        }
    }

    fun findConsumableWithTitle(title: String) {
        viewModelScope.launch {
            mainRepository.findConsumableWithTitle(title)
                .onEach { result ->
                    Timber.d("$result")
                }
                .launchIn(viewModelScope)
        }
    }

    fun insert(userConsumable: UserConsumableEntity) {
        viewModelScope.launch {
            mainRepository.insertUserConsumable(userConsumable)
                .onEach { result ->
                    _dataState.value = result
                }
                .launchIn(viewModelScope)
        }
    }
}