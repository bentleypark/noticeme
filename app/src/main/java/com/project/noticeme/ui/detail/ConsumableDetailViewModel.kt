package com.project.noticeme.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.noticeme.common.base.BaseViewModel
import com.project.noticeme.data.repository.MainRepository
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.data.state.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ConsumableDetailViewModel @ViewModelInject
constructor(
    private val mainRepository: MainRepository
): BaseViewModel() {

    private val _userConsumableItem = MutableLiveData<DataState<UserConsumableEntity>>()
    val userConsumableItem: LiveData<DataState<UserConsumableEntity>>
    get() = _userConsumableItem

    private val _dataStateForUpdate = MutableLiveData<DataState<Boolean>>()
    val dataStateForUpdate: LiveData<DataState<Boolean>>
    get() = _dataStateForUpdate

    fun getWithTitle(title: String) {
        viewModelScope.launch{
            mainRepository.getUserConsumableWithTitle(title)
                .onEach { dataState ->
                    _userConsumableItem.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    fun update(item: UserConsumableEntity) {
        viewModelScope.launch{
            mainRepository.update(item)
                .onEach { dataState ->
                    _dataStateForUpdate.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }
}