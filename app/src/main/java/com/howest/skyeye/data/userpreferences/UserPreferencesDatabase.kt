package com.howest.skyeye.data.userpreferences


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserPreferences::class], version = 2, exportSchema = false)
abstract class UserPreferencesDatabase : RoomDatabase() {

    abstract fun userPreferencesDao(): UserPreferencesDao

    companion object {
        @Volatile
        private var INSTANCE: UserPreferencesDatabase? = null

        fun getDatabase(context: Context): UserPreferencesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserPreferencesDatabase::class.java,
                    "user_preferences_database"
                )
                    .fallbackToDestructiveMigration() // Allow for destructive migrations
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}