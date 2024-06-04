package com.howest.skyeye.data

import android.content.Context
import com.howest.skyeye.data.useraccounts.UsersDatabase
import com.howest.skyeye.data.useraccounts.UsersRepository
import com.howest.skyeye.data.useraccounts.UsersRepositoryInterface
import com.howest.skyeye.data.userpreferences.UserPreferencesDatabase
import com.howest.skyeye.data.userpreferences.UserPreferencesRepository
import com.howest.skyeye.data.userpreferences.UserPreferencesRepositoryInterface

interface AppContainer {
    val userPreferencesRepositoryInterface: UserPreferencesRepositoryInterface
    val usersRepositoryInterface: UsersRepositoryInterface
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val userPreferencesRepositoryInterface: UserPreferencesRepositoryInterface by lazy {
        UserPreferencesRepository(UserPreferencesDatabase.getDatabase(context).userPreferencesDao())
    }
    override val usersRepositoryInterface: UsersRepositoryInterface by lazy {
        UsersRepository(UsersDatabase.getDatabase(context).userDao())
    }
}
