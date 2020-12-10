package com.project.noticeme.ui.category.initialdata

import android.content.Context
import com.project.noticeme.R
import com.project.noticeme.data.model.ConsumableCategory
import okhttp3.internal.toImmutableList

object ConsumableCategoryData {
    private val dataList = mutableListOf<ConsumableCategory>()

    fun fetchData(context: Context): List<ConsumableCategory> {

        if (dataList.size != 5) {
            dataList.add(
                ConsumableCategory(
                    0,
                    context.getString(R.string.category_title1),
                    R.drawable.ic_category_bath
                )
            )

            dataList.add(
                ConsumableCategory(
                    1,
                    context.getString(R.string.category_title2),
                    R.drawable.ic_category_kitchen
                )
            )

            dataList.add(
                ConsumableCategory(
                    2,
                    context.getString(R.string.category_title3),
                    R.drawable.ic_category_personal_hygiene
                )
            )

            dataList.add(
                ConsumableCategory(
                    3,
                    context.getString(R.string.category_title4),
                    R.drawable.ic_category_bedroom
                )
            )

            dataList.add(
                ConsumableCategory(
                    4,
                    context.getString(R.string.personal_category_title),
                    R.drawable.ic_category_user_custom
                )
            )
        }

        return dataList.toImmutableList()
    }
}