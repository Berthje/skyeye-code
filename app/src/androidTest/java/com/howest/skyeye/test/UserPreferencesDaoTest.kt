package com.howest.skyeye.test

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.howest.skyeye.data.userpreferences.UserPreferences
import com.howest.skyeye.data.userpreferences.UserPreferencesDao
import com.howest.skyeye.data.userpreferences.UserPreferencesDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserPreferencesDaoTest {

    private lateinit var userPreferencesDao: UserPreferencesDao
    private lateinit var userPreferencesDatabase: UserPreferencesDatabase

    @Before
    fun createDb() {
        val context : Context = ApplicationProvider.getApplicationContext()
        userPreferencesDatabase = Room.inMemoryDatabaseBuilder(context, UserPreferencesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userPreferencesDao = userPreferencesDatabase.userPreferencesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        userPreferencesDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertUserPreferencesAndReadInList() = runBlocking {
        val userPreferences = UserPreferences(1, true)
        userPreferencesDao.insertUserPreferences(userPreferences)
        val byId = userPreferencesDao.getUserPreferences(1).first()
        assertEquals(byId, userPreferences)
    }

    @Test
    @Throws(Exception::class)
    fun updateUserPreferencesAndReadInList() = runBlocking {
        val userPreferences = UserPreferences(1, true)
        userPreferencesDao.insertUserPreferences(userPreferences)
        val updatedUserPreferences = UserPreferences(1, false)
        userPreferencesDao.updateUserPreferences(updatedUserPreferences)
        val byId = userPreferencesDao.getUserPreferences(1).first()
        assertEquals(byId, updatedUserPreferences)
    }

    @Test
    @Throws(Exception::class)
    fun getLastUserPreferences() = runBlocking {
        val userPreferences1 = UserPreferences(1, true)
        val userPreferences2 = UserPreferences(2, false)
        userPreferencesDao.insertUserPreferences(userPreferences1)
        userPreferencesDao.insertUserPreferences(userPreferences2)
        val lastUserPreferences = userPreferencesDao.getLastUserPreferences().first()
        assertEquals(lastUserPreferences, userPreferences2)
    }
}