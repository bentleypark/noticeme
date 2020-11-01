package com.project.noticeme.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.noticeme.data.room.ConsumableEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ConsumableEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "image")
    var image: Int,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "duration")
    val duration: String
) {
    companion object {
        const val TABLE_NAME = "consumables"
    }
}
