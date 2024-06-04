package com.howest.skyeye.data.userpreferences

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPreferencesDao {
    @Query("SELECT * FROM user_preferences WHERE id = :id")
    fun getUserPreferences(id: Int): Flow<UserPreferences?>

    @Query("SELECT * FROM user_preferences ORDER BY id DESC LIMIT 1")
    fun getLastUserPreferences(): Flow<UserPreferences?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserPreferences(userPreferences: UserPreferences): Long

    @Update
    suspend fun updateUserPreferences(userPreferences: UserPreferences)

    @Query("SELECT id FROM user_preferences ORDER BY id DESC LIMIT 1")
    suspend fun getLastInsertedId(): Int?

}