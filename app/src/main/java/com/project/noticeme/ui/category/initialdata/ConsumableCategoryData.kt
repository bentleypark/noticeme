package com.project.noticeme.ui.category.initialdata

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
                ConsumableCategory(4, "나의 목록", R.drawable.ic_category_user_custom)
            )
        }

        return dataList.toImmutableList()
    }
}