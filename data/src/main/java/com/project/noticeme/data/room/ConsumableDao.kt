package com.project.noticeme.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ConsumableDao {

    @Query("SELECT * FROM consumables")
    suspend fun get(): List<ConsumableEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(blogEntity: ConsumableEntity)
}