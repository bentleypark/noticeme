package com.project.noticeme.ui.category.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.noticeme.common.base.BaseViewModel
import com.project.noticeme.common.utils.preference.SharedPreferenceManager
import com.project.noticeme.data.repository.MainRepository
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.data.state.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CategoryDetailViewModel @ViewModelInject
constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val _consumableList = MutableLiveData<DataState<List<ConsumableEntity>>>()

    val consumableList: LiveData<DataState<List<ConsumableEntity>>>
        get() = _consumableList

    private val _dataState = MutableLiveData<DataState<Boolean>>()

    val dataState: LiveData<DataState<Boolean>>
        get() = _dataState

    private val userConsumableList = MutableLiveData<List<UserConsumableEntity>>()

    @Inject
    lateinit var pref: SharedPreferenceManager

    init {
        getUserConsumableData()
    }

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

    private fun getUserConsumableData() {
        Timber.d("getUserConsumableData")
        viewModelScope.launch {
            mainRepository.getUserConsumableFromDetail()
                .onEach { dataState ->
                    userConsumableList.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    fun checkIfItemIsAlreadyInserted(title: String) =
        (userConsumableList.value!!.find { it.title == title } != null)

    fun checkIsNotificationSettingOn() = pref.getNotificationSetting()
}