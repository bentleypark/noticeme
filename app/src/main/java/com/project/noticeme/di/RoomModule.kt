package com.project.noticeme.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.project.noticeme.data.room.ConsumableDao
import com.project.noticeme.data.room.ConsumableDatabase
import com.project.noticeme.data.room.SearchHistoryDao
import com.project.noticeme.data.room.UserConsumableDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideConsumableDb(@ApplicationContext context: Context): ConsumableDatabase {
        return Room
            .databaseBuilder(
                context,
                ConsumableDatabase::class.java,
                ConsumableDatabase.DATABASE_NAME
            )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .addMigrations(migration_1_2)
            .build()
    }

    private val migration_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE consumables_new (id INTEGER NOT NULL, title TEXT NOT NULL, imageTitle TEXT NOT NULL, category TEXT NOT NULL, duration LONG NOT NULL, PRIMARY KEY(id))")
            database.execSQL("INSERT INTO consumables_new (id, title, imageTitle, category, duration) SELECT id, title, '', category, duration FROM consumables")
            database.execSQL("DROP TABLE consumables")
            database.execSQL("ALTER TABLE consumables_new RENAME TO consumables")

            database.execSQL("CREATE TABLE userConsumables_new (id INTEGER NOT NULL, title TEXT NOT NULL, imageTitle TEXT NOT NULL, category TEXT NOT NULL, duration LONG NOT NULL, startDate LONG NOT NULL, endDate LONG NOT NULL, priority INTEGER NOT NULL, PRIMARY KEY(id))")
            database.execSQL("INSERT INTO userConsumables_new (id, title, imageTitle, category, duration, startDate, endDate, priority) SELECT id, title, '', category, duration, startDate, endDate, priority FROM userConsumables")
            database.execSQL("DROP TABLE userConsumables")
            database.execSQL("ALTER TABLE userConsumables_new RENAME TO userConsumables")
        }
    }

    @Singleton
    @Provides
    fun provideConsumableDAO(consumableDatabase: ConsumableDatabase): ConsumableDao {
        return consumableDatabase.consumableDao()
    }


    @Singleton
    @Provides
    fun provideUserConsumableDao(consumableDatabase: ConsumableDatabase): UserConsumableDao {
        return consumableDatabase.userConsumableDao()
    }

    @Singleton
    @Provides
    fun provideSearchHistoryDao(consumableDatabase: ConsumableDatabase): SearchHistoryDao {
        return consumableDatabase.searchHistoryDao()
    }
}