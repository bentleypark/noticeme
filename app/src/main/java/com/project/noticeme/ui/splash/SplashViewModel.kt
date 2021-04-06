package com.project.noticeme.ui.splash

import androidx.lifecycle.*
import com.project.noticeme.common.utils.preference.SharedPreferenceManager
import com.project.noticeme.data.repository.MainRepository
import com.project.noticeme.data.room.ConsumableEntity
import com.project.noticeme.data.state.DataState
import com.project.noticeme.ui.splash.initialdata.InitialConsumableData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val pref: SharedPreferenceManager
) : ViewModel() {

    var dataList = emptyList<ConsumableEntity>()
    var consumableList = emptyList<ConsumableEntity>()

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

    fun getAll() {
        viewModelScope.launch {
            consumableList = mainRepository.getAll()
        }
    }


    fun checkIsInitialDataSet() {
        if (!pref.getInitialData()) {
            viewModelScope.launch {
                val deferredList = ArrayList<Deferred<*>>()
                deferredList.add(async { getAll() })
                delay(1000)
                deferredList.add(async {
                    Timber.d(consumableList.toString())
                    if (consumableList.isEmpty()) {
                        dataList = InitialConsumableData.fetchData()
                        insertData(dataList)
                    }
                })

                deferredList.joinAll()
            }
        }
    }
}