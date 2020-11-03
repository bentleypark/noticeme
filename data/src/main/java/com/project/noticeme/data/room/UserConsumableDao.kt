package com.project.noticeme.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface UserConsumableDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userConsumable: UserConsumableEnityty)
}