package com.howest.skyeye.data.useraccounts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    //for single user insert
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long

    //for list of users insert
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUserAll(users: List<User>): List<Long>

    //checking user exist or not in our db
    @Query("SELECT * FROM users WHERE email LIKE :email AND password LIKE :password")
    suspend fun readLoginData(email: String, password: String): User


    //getting user data details
    @Query("select * from users where id Like :id")
    suspend fun getUserDataDetails(id:Long): User

    //deleting all user from db
    suspend @Query("DELETE FROM users")
    fun deleteAll()


}