package com.project.noticeme.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ConsumableEntity::class, UserConsumableEntity::class], version = 1)
abstract class ConsumableDatabase : RoomDatabase() {

    abstract fun consumableDao(): ConsumableDao

    abstract fun userConsumableDao(): UserConsumableDao

    companion object {
        val DATABASE_NAME: String = "consumable_db"
    }
}