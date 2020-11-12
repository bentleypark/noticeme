package com.project.noticeme.ui.home.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.project.noticeme.App
import com.project.noticeme.R
import com.project.noticeme.common.base.BaseViewModel
import com.project.noticeme.common.utils.preference.PreferenceUtil
import com.project.noticeme.common.utils.preference.SharedPreferenceManager
import com.project.noticeme.data.repository.MainRepository
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.room.UserConsumableEntity
import com.project.noticeme.data.state.DataState
import com.project.noticeme.ui.home.initialdata.InitialConsumableData
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import timber.log.Timber
import java.util.concurrent.TimeUnit

class HomeViewModel @ViewModelInject
constructor(
    private val mainRepository: MainRepository,
    private val pref: SharedPreferenceManager,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _consumableList = MutableLiveData<DataState<List<UserConsumableEntity>>>()
    val consumableList: LiveData<DataState<List<UserConsumableEntity>>>?
        get() = _consumableList

    var dataList = emptyList<ConsumableEntity>()

    private val _dataState = MutableLiveData<DataState<String>>()

    val dataState: LiveData<DataState<String>>
        get() = _dataState

    private val _deleteResult = MutableLiveData<DataState<Boolean>>()
    val deleteResult: LiveData<DataState<Boolean>>
        get() = _deleteResult

    private val _dataStateForUpdate = MutableLiveData<DataState<Boolean>>()
    val dataStateForUpdate: LiveData<DataState<Boolean>>
        get() = _dataStateForUpdate

    init {
        checkIsInitialDataSet()
        getUserConsumableData()
    }

    private fun insertData(list: List<ConsumableEntity>) {
        viewModelScope.launch {
            mainRepository.insertConsumable(list)
                .onEach { dataState ->
                    _dataState.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    private fun checkIsInitialDataSet() {
        val result = pref.getInitialData()
        if (!result) {
            dataList = InitialConsumableData.fetchData()
            insertData(dataList)
        }
    }

    fun getUserConsumableData() {
        viewModelScope.launch {
            mainRepository.getUserConsumable()
                .onEach { dataState ->
                    _consumableList.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    fun delete(item: UserConsumableEntity) {
        viewModelScope.launch {
            mainRepository.delete(item)
                .onEach { dataState ->
                    _deleteResult.value = dataState
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