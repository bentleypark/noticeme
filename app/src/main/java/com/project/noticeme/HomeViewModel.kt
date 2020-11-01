package com.project.noticeme

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.project.noticeme.common.base.BaseViewModel
import com.project.noticeme.data.model.Consumable
import com.project.noticeme.data.repository.MainRepository
import com.project.noticeme.data.room.ConsumableEntity
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import java.util.concurrent.TimeUnit

class HomeViewModel @ViewModelInject
constructor(
    private val mainRepository: MainRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _materialList = liveData {
        val dataList = mutableListOf<ConsumableEntity>()
//        for (i in 0..7) {
        dataList.add(
            ConsumableEntity(
                0,
                "칫솔",
                R.drawable.ic_img_toothbrush,
                "욕실",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 90
            )
        )

        dataList.add(
            ConsumableEntity(
                1,
                "면도기",
                R.drawable.ic_img_razor,
                "개인위생",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 14
            )
        )

        dataList.add(
            ConsumableEntity(
                2,
                "수세미",
                R.drawable.ic_img_scrubbers,
                "주방",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 30
            )
        )

        dataList.add(
            ConsumableEntity(
                3,
                "베개 커버",
                R.drawable.ic_img_pillow_cover,
                "침실",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 7
            )
        )
        insertData(dataList)
        emit(dataList.toImmutableList())
    }
    val materialList: LiveData<List<ConsumableEntity>>
        get() = _materialList

    fun insertData(list: List<ConsumableEntity>) {
        viewModelScope.launch {
            mainRepository.insertConsumable(list)
        }
    }
}