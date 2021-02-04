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

    @Query("SELECT * FROM consumables WHERE category LIKE :search")
    suspend fun findConsumableWithCategory(search: String): List<ConsumableEntity>

    @Query("SELECT * FROM consumables WHERE title LIKE '%' || :search || '%'")
    suspend fun findConsumableWithTitle(search: String): List<ConsumableEntity>

    @Query("SELECT * FROM consumables ORDER BY id DESC LIMIT 1")
    suspend fun getLastItem(): ConsumableEntity
}