package com.project.noticeme.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userHistory: SearchHistoryEntity)

    @Query("DELETE FROM searchHistory")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(userHistory: SearchHistoryEntity)

    @Query("SELECT * FROM searchHistory ORDER BY id DESC")
    suspend fun get(): List<SearchHistoryEntity>
}