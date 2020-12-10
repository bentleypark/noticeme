package com.project.noticeme.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserConsumableEntity.TABLE_NAME)
data class UserConsumableEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

//    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "image")
    val image: Int,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "duration")
    val duration: Long,

    @ColumnInfo(name = "startDate")
    val startDate: Long,

    @ColumnInfo(name = "endDate")
    val endDate: Long,

    @ColumnInfo(name = "priority")
    val priority: Int
) {
    companion object {
        const val TABLE_NAME = "userConsumables"
    }
}