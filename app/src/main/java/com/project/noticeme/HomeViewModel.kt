package com.project.noticeme

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.project.noticeme.common.base.BaseViewModel
import com.project.noticeme.data.model.Material
import okhttp3.internal.toImmutableList

class HomeViewModel @ViewModelInject
constructor() : BaseViewModel() {

    private val _materialList = liveData {
        val dataList = mutableListOf<Material>()
        for (i in 0..7) {
            dataList.add(
                Material(
                    i, "칫솔", R.drawable.ic_img_toothbrush, "욕실", "-72일"
                )
            )
        }
        emit(dataList.toImmutableList())
    }
    val materialList: LiveData<List<Material>>
        get() = _materialList
}