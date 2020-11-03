package com.project.noticeme.ui.home.initialdata

import com.project.noticeme.R
import com.project.noticeme.data.room.ConsumableEntity
import okhttp3.internal.toImmutableList
import java.util.concurrent.TimeUnit

object InitialConsumableData {
    private val dataList = mutableListOf<ConsumableEntity>()

    fun fetchData(): List<ConsumableEntity> {
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
                "치간칫솔",
                R.drawable.ic_img_interdental_toothbrush,
                "욕실",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 7
            )
        )

        dataList.add(
            ConsumableEntity(
                2,
                "샤워타월",
                R.drawable.ic_img_shower_towel,
                "욕실",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 90
            )
        )

        dataList.add(
            ConsumableEntity(
                10,
                "수세미",
                R.drawable.ic_img_scrubbers,
                "주방",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 30
            )
        )

        dataList.add(
            ConsumableEntity(
                11,
                "행주",
                R.drawable.ic_img_scrubbers,
                "주방",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 7
            )
        )

        dataList.add(
            ConsumableEntity(
                12,
                "고무장갑",
                R.drawable.ic_img_scrubbers,
                "주방",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 90
            )
        )

        dataList.add(
            ConsumableEntity(
                13,
                "플라스틱용기",
                R.drawable.ic_img_scrubbers,
                "주방",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 90
            )
        )

        dataList.add(
            ConsumableEntity(
                14,
                "플라스틱용기",
                R.drawable.ic_img_scrubbers,
                "주방",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 90
            )
        )

        dataList.add(
            ConsumableEntity(
                20,
                "면도날",
                R.drawable.ic_img_razor,
                "개인위생",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 14
            )
        )

        dataList.add(
            ConsumableEntity(
                21,
                "메이크업브러쉬",
                R.drawable.ic_img_makeup_brush,
                "개인위생",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 720
            )
        )

        dataList.add(
            ConsumableEntity(
                22,
                "소프트렌즈",
                R.drawable.ic_img_makeup_brush,
                "개인위생",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 300
            )
        )

        dataList.add(
            ConsumableEntity(
                23,
                "하드렌즈",
                R.drawable.ic_img_makeup_brush,
                "개인위생",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 720
            )
        )

        dataList.add(
            ConsumableEntity(
                24,
                "렌즈케이스",
                R.drawable.ic_img_makeup_brush,
                "개인위생",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 90
            )
        )

        dataList.add(
            ConsumableEntity(
                30,
                "이불",
                R.drawable.ic_img_blanket,
                "침실",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 60
            )
        )

        dataList.add(
            ConsumableEntity(
                31,
                "베개커버",
                R.drawable.ic_img_blanket,
                "침실",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 7
            )
        )

        dataList.add(
            ConsumableEntity(
                32,
                "침대시트",
                R.drawable.ic_img_blanket,
                "침실",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 10
            )
        )

        return dataList.toImmutableList()
    }
}