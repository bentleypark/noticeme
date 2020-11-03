package com.project.noticeme.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserConsumableDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userConsumable: UserConsumableEntity)

    @Query("SELECT * FROM userConsumables")
    suspend fun get(): List<UserConsumableEntity>
}