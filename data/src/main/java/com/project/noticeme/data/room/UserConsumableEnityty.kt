package com.project.noticeme.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = ConsumableEntity.TABLE_NAME)
data class UserConsumableEnityty(
    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "image")
    var image: Int,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "duration")
    val duration: Long,

    @ColumnInfo(name = "startDate")
    val startDate: Long,

    @ColumnInfo(name = "priority")
    val priority: String
) {
    companion object {
        const val TABLE_NAME = "userConsumables"
    }
}