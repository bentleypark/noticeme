package com.project.noticeme.ui.home.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.project.noticeme.App
import com.project.noticeme.R
import com.project.noticeme.common.base.BaseViewModel
import com.project.noticeme.common.utils.preference.PreferenceUtil
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
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _consumableList = MutableLiveData<DataState<List<UserConsumableEntity>>>()
    val consumableList: LiveData<DataState<List<UserConsumableEntity>>>?
        get() = _consumableList

    var dataList = emptyList<ConsumableEntity>()

    private val _dataState = MutableLiveData<DataState<String>>()

    val dataState: LiveData<DataState<String>>
        get() = _dataState

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
        val result = PreferenceUtil.getInitialData(App.globalApplicationContext)
        Timber.d("$result")
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

    fun flushUserConsumableData() {
        _consumableList.value = null
    }
}