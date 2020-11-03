package com.project.noticeme.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserConsumableEntity.TABLE_NAME)
data class UserConsumableEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

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