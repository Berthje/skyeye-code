package com.howest.skyeye.data.useraccounts

interface UsersRepositoryInterface {
    suspend fun addUser(user: User): Long

    suspend fun addUserList(users: List<User>): List<Long>

    suspend fun deleteUser(user: User)

    suspend fun verifyLoginUser(email: String, password: String): User
    suspend fun getUserDataDetails(id: Long): User
}