package com.project.noticeme.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.noticeme.data.room.SearchHistoryEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "keyword")
    val keyword: String,
) {
    companion object {
        const val TABLE_NAME = "searchHistory"
    }
}