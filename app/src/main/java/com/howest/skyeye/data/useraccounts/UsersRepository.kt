package com.howest.skyeye.data.useraccounts

import android.database.sqlite.SQLiteConstraintException

class UsersRepository(private var userDao: UserDao) : UsersRepositoryInterface {

    override suspend fun addUser(user: User): Long {
        return try {
            // Try to add the user to the database
            userDao.insertUser(user)
        } catch (e: SQLiteConstraintException) {
            // If a SQLiteConstraintException is thrown, return a special value to indicate the error
            -1L
        }
    }

    override suspend fun addUserList(users:List<User>): List<Long> {
        return userDao.insertUserAll(users)
    }

    override suspend fun deleteUser(user: User) {
        //TODO("Not yet implemented")
    }

    override suspend fun verifyLoginUser(email:String, password:String): User {
        return userDao.readLoginData(email = email, password = password )
    }

    override suspend fun getUserDataDetails(id:Long): User {
        return userDao.getUserDataDetails(id)
    }
}