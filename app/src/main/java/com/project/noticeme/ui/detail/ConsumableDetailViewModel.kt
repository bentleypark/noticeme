package com.project.noticeme.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.noticeme.common.base.BaseViewModel
import com.project.noticeme.data.repository.MainRepository
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.data.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConsumableDetailViewModel @Inject
constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    private val _userConsumableItem = MutableLiveData<DataState<UserConsumableEntity>>()
    val userConsumableItem: LiveData<DataState<UserConsumableEntity>>
        get() = _userConsumableItem

    private val _dataStateForUpdate = MutableLiveData<DataState<Boolean>>()
    val dataStateForUpdate: LiveData<DataState<Boolean>>
        get() = _dataStateForUpdate

    private val _deleteResult = MutableLiveData<DataState<Boolean>>()
    val deleteResult: LiveData<DataState<Boolean>>
        get() = _deleteResult

    fun getWithTitle(title: String) {
        viewModelScope.launch {
            mainRepository.getUserConsumableWithTitle(title)
                .onEach { dataState ->
                    _userConsumableItem.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    fun update(item: UserConsumableEntity) {
        viewModelScope.launch {
            mainRepository.update(item)
                .onEach { dataState ->
                    _dataStateForUpdate.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    fun delete(item: UserConsumableEntity) {
        viewModelScope.launch {
            mainRepository.deleteFromDetail(item)
                .onEach { dataState ->
                    _deleteResult.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }
}