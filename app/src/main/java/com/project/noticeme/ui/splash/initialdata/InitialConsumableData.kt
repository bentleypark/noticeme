package com.project.noticeme.ui.splash.initialdata

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
                0,
                "치간칫솔",
                R.drawable.ic_img_interdental_toothbrush,
                "욕실",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 7
            )
        )

        dataList.add(
            ConsumableEntity(
                0,
                "샤워타월",
                R.drawable.ic_img_shower_towel,
                "욕실",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 90
            )
        )

        dataList.add(
            ConsumableEntity(
                0,
                "수세미",
                R.drawable.ic_img_scrubbers,
                "주방",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 30
            )
        )

        dataList.add(
            ConsumableEntity(
                0,
                "행주",
                R.drawable.ic_img_dishcloth,
                "주방",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 7
            )
        )

        dataList.add(
            ConsumableEntity(
                0,
                "고무장갑",
                R.drawable.ic_img_rubber_glove,
                "주방",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 90
            )
        )

        dataList.add(
            ConsumableEntity(
                0,
                "플라스틱용기",
                R.drawable.ic_img_plastic,
                "주방",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 90
            )
        )


        dataList.add(
            ConsumableEntity(
                0,
                "면도날",
                R.drawable.ic_img_razor,
                "개인위생",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 14
            )
        )

        dataList.add(
            ConsumableEntity(
                0,
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
                R.drawable.ic_img_soft_lens,
                "개인위생",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 300
            )
        )

        dataList.add(
            ConsumableEntity(
                0,
                "하드렌즈",
                R.drawable.ic_img_hard_lens,
                "개인위생",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 720
            )
        )

        dataList.add(
            ConsumableEntity(
                0,
                "렌즈케이스",
                R.drawable.ic_img_makeup_brush,
                "개인위생",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 90
            )
        )

        dataList.add(
            ConsumableEntity(
                0,
                "브래지어",
                R.drawable.ic_img_underware,
                "개인위생",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 180
            )
        )

        dataList.add(
            ConsumableEntity(
                0,
                "이불",
                R.drawable.ic_img_blanket,
                "침실",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 60
            )
        )

        dataList.add(
            ConsumableEntity(
                0,
                "베개커버",
                R.drawable.ic_img_pillow_cover,
                "침실",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 7
            )
        )

        dataList.add(
            ConsumableEntity(
                0,
                "침대시트",
                R.drawable.ic_img_bed_sheet,
                "침실",
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 10
            )
        )

        return dataList.toImmutableList()
    }
}