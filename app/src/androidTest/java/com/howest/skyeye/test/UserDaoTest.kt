package com.howest.skyeye.test

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.howest.skyeye.data.useraccounts.User
import com.howest.skyeye.data.useraccounts.UserDao
import com.howest.skyeye.data.useraccounts.UsersDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var userDao: UserDao
    private lateinit var usersDatabase: UsersDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        usersDatabase = Room.inMemoryDatabaseBuilder(context, UsersDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userDao = usersDatabase.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        usersDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertUserAndReadInList() = runBlocking {
        val user = User(1, "test@test.com", "password")
        userDao.insertUser(user)
        val byEmail = userDao.readLoginData("test@test.com", "password")
        assertEquals(byEmail, user)
    }

    @Test
    @Throws(Exception::class)
    fun insertUsersAndReadInList() = runBlocking {
        val users = listOf(
            User(1, "test1@test.com", "password1"),
            User(2, "test2@test.com", "password2")
        )
        userDao.insertUserAll(users)
        val byEmail1 = userDao.readLoginData("test1@test.com", "password1")
        val byEmail2 = userDao.readLoginData("test2@test.com", "password2")
        assertEquals(byEmail1, users[0])
        assertEquals(byEmail2, users[1])
    }

    @Test
    @Throws(Exception::class)
    fun deleteUserAndReadInList() = runBlocking {
        val user = User(1, "test@test.com", "password")
        userDao.insertUser(user)
        userDao.deleteAll()
        val byEmail = userDao.readLoginData("test@test.com", "password")
        assertEquals(byEmail, null)
    }

    @Test
    @Throws(Exception::class)
    fun insertUserWithSameEmail() = runBlocking {
        val user1 = User(1, "test@test.com", "password")
        val user2 = User(2, "test@test.com", "password2")
        userDao.insertUser(user1)
        try {
            userDao.insertUser(user2)
            fail("Should have thrown an exception because email is not unique")
        } catch (e: Exception) {
            assert(e.message != null)
        }
    }
}
