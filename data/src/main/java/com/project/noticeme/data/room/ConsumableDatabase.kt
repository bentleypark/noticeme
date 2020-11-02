package com.project.noticeme.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ConsumableEntity::class], version = 1)
abstract class ConsumableDatabase : RoomDatabase() {

    abstract fun consumableDao(): ConsumableDao

    companion object{
        val DATABASE_NAME: String = "consumable_db"
    }
}