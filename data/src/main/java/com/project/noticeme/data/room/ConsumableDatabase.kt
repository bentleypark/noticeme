package com.project.noticeme.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ConsumableEntity::class, UserConsumableEntity::class, SearchHistoryEntity::class],
    version = 1, exportSchema = false
)
abstract class ConsumableDatabase : RoomDatabase() {

    abstract fun consumableDao(): ConsumableDao

    abstract fun userConsumableDao(): UserConsumableDao

    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        val DATABASE_NAME: String = "consumable_db"
    }
}