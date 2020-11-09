package com.project.noticeme.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.noticeme.data.room.ConsumableEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ConsumableEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "image")
    var image: Int,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "duration")
    val duration: Long
) {
    companion object {
        const val TABLE_NAME = "consumables"
    }
}
