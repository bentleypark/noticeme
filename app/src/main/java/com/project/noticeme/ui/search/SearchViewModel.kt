package com.project.noticeme.ui.search

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
import timber.log.Timber


class SearchViewModel @ViewModelInject
constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val _searchList = MutableLiveData<DataState<List<ConsumableEntity>>>()

    val searchList: LiveData<DataState<List<ConsumableEntity>>>
        get() = _searchList

    fun searchWithTitle(title: String) {
        viewModelScope.launch {
            mainRepository.findConsumableWithTitle(title)
                .onEach { dataState ->
                    Timber.d("$dataState")
                    _searchList.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }
}