package com.project.noticeme.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.noticeme.common.utils.preference.SharedPreferenceManager
import com.project.noticeme.data.repository.MainRepository
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.state.DataState
import com.project.noticeme.ui.splash.initialdata.InitialConsumableData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val pref: SharedPreferenceManager
) : ViewModel() {

    var dataList = emptyList<ConsumableEntity>()

    private fun insertData(list: List<ConsumableEntity>) {
        viewModelScope.launch {
            mainRepository.insertConsumable(list)
                .onEach { dataState ->
                    when (dataState) {
                        is DataState.Success<Boolean> -> {
                            pref.setInitialData(true)
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    fun checkIsInitialDataSet() {
        val result = pref.getInitialData()
        if (!result) {
            dataList = InitialConsumableData.fetchData()
            insertData(dataList)
        }
    }
}