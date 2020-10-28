package com.project.noticeme.common.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class BaseViewModel : ViewModel() {
    /**
     * CoroutineScope 내부 Exception 처리 Handler
     */
    private val coroutineExceptionHanlder =
        CoroutineExceptionHandler { _, throwable ->
            println("Exception Caught ${throwable.printStackTrace()}")
        }

    private val viewModelJob = SupervisorJob()

    /**
     * Dispatchers 선언 (Normal Dispatchers + CoroutineExceptionHandler)
     */
    protected val ioDispatchers = Dispatchers.IO + coroutineExceptionHanlder + viewModelJob
    protected val uiDispatchers = Dispatchers.Main + coroutineExceptionHanlder + viewModelJob

    /**
     * Clear Rx when called onCleared
     */
    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        viewModelJob.cancel()
    }
}
