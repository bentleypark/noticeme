package com.project.noticeme.ui.add

import com.project.noticeme.R
import com.project.noticeme.data.model.ConsumableCategory
import okhttp3.internal.toImmutableList

object ConsumableCategoryData {
    private val dataList = mutableListOf<ConsumableCategory>()

    fun fetchData(): List<ConsumableCategory> {

        if (dataList.size != 5) {
            dataList.add(
                ConsumableCategory(0, "욕실", R.drawable.ic_category_bath)
            )

            dataList.add(
                ConsumableCategory(1, "주방", R.drawable.ic_category_kitchen)
            )

            dataList.add(
                ConsumableCategory(2, "개인위생", R.drawable.ic_category_personal_hygiene)
            )

            dataList.add(
                ConsumableCategory(3, "침실", R.drawable.ic_category_bedroom)
            )

            dataList.add(
                ConsumableCategory(4, "직접입력", R.drawable.ic_category_add)
            )
        }

        return dataList.toImmutableList()
    }
}