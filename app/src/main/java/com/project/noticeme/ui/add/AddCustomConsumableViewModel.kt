package com.project.noticeme.ui.add

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

class AddCustomConsumableViewModel @ViewModelInject
constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    private val _dataState = MutableLiveData<DataState<Boolean>>()

    val dataState: LiveData<DataState<Boolean>>
        get() = _dataState

    private val _dataStateUserConsumable = MutableLiveData<DataState<Boolean>>()

    val dataStateUserConsumable: LiveData<DataState<Boolean>>
        get() = _dataStateUserConsumable

    fun insert(item: ConsumableEntity) {
        viewModelScope.launch {
            mainRepository.insertCustomConsumable(item)
                .onEach { dataState ->
                    _dataState.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    fun insertUserConsumable(userConsumable: UserConsumableEntity) {
        viewModelScope.launch {
            mainRepository.insertUserConsumable(userConsumable)
                .onEach { result ->
                    _dataStateUserConsumable.value = result
                }
                .launchIn(viewModelScope)
        }
    }
}