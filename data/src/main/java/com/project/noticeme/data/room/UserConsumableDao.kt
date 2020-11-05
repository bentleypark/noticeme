package com.project.noticeme.data.room

import androidx.room.*

@Dao
interface UserConsumableDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userConsumable: UserConsumableEntity)

    @Query("SELECT * FROM userConsumables ORDER BY startDate DESC")
    suspend fun get(): List<UserConsumableEntity>

    @Delete
    suspend fun delete(item: UserConsumableEntity)
}