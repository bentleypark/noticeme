package com.project.noticeme.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.noticeme.common.base.BaseViewModel
import com.project.noticeme.data.repository.MainRepository
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.room.SearchHistoryEntity
import com.project.noticeme.data.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject
constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val _searchList = MutableLiveData<DataState<List<ConsumableEntity>>>()

    val searchList: LiveData<DataState<List<ConsumableEntity>>>
        get() = _searchList

    private val _searchHistoryList = MutableLiveData<DataState<List<SearchHistoryEntity>>>()

    val searchHistoryList: LiveData<DataState<List<SearchHistoryEntity>>>
        get() = _searchHistoryList

    private val _resultInsert = MutableLiveData<DataState<Boolean>>()

    val resultInsert: LiveData<DataState<Boolean>>
        get() = _resultInsert

    private val _selectedHistoryTitle = MutableLiveData<String>()
    val selectedHistoryTitle: LiveData<String>
        get() = _selectedHistoryTitle


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

    fun insertSearchHistory(userHistory: SearchHistoryEntity) {
        viewModelScope.launch {
            mainRepository.insertSearchHistory(userHistory)
                .onEach { dataState ->
                    _resultInsert.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    fun getSearchHistory() {
        viewModelScope.launch {
            mainRepository.getSearchHistory()
                .onEach { dataState ->
                    _searchHistoryList.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    fun deleteAllHistory() {
        viewModelScope.launch {
            mainRepository.deleteAllHistory()
                .onEach {}
                .launchIn(viewModelScope)
        }
    }

    fun deleteHistory(userHistory: SearchHistoryEntity) {
        viewModelScope.launch {
            mainRepository.deleteHistory(userHistory)
                .onEach {}
                .launchIn(viewModelScope)
        }
    }

    fun updateHistoryTitle(title: String) {
        _selectedHistoryTitle.value = title
    }

    init {
        getSearchHistory()
    }
}