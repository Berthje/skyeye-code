package com.howest.skyeye.data.userpreferences

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(private val userPreferencesDao: UserPreferencesDao) :
    UserPreferencesRepositoryInterface {
    override val userPreferences: Flow<UserPreferences> = userPreferencesDao.getLastUserPreferences().map { it ?: UserPreferences(is_dark_mode = false) }
    override suspend fun insertUserPreferences(userPreferences: UserPreferences): Long {
        return userPreferencesDao.insertUserPreferences(userPreferences)
    }

    override suspend fun updateUserPreferences(userPreferences: UserPreferences) {
        userPreferencesDao.updateUserPreferences(userPreferences)
    }

    override suspend fun getLastInsertedId(): Int? {
        return userPreferencesDao.getLastInsertedId()
    }
}