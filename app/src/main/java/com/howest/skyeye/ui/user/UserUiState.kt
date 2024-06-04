package com.howest.skyeye.ui.user

data class UserUiState(
    val email: String = "",
    val password: String = "",
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
)