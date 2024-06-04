package com.howest.skyeye.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.howest.skyeye.data.useraccounts.User
import com.howest.skyeye.data.useraccounts.UsersRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest

class UserViewModel(private val usersRepository: UsersRepositoryInterface) : ViewModel() {
    private val _uiState = MutableStateFlow(UserUiState())
    val userUiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = UserUiState()
    }

    suspend fun login(email: String, password: String) {
        _uiState.value = UserUiState(isLoading = true, error = "")
        val hashedPassword = hashPassword(password)
        viewModelScope.launch {
            try {
                val user = usersRepository.verifyLoginUser(email, hashedPassword)
                _uiState.value = UserUiState(email = user.email, password = user.password, isLoggedIn = true, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = UserUiState(error = "Login failed: ${e.message}", isLoading = false)
            }
            println("login: ${_uiState.value}")
        }
    }

    suspend fun register(email: String, password: String) {
        _uiState.value = UserUiState(isLoading = true, error = "")
        val hashedPassword = hashPassword(password)
        viewModelScope.launch {
            val userId = usersRepository.addUser(User(email = email, password = hashedPassword))
            if (userId == -1L) {
                // If the addUser method returned -1, show an error message
                _uiState.value = UserUiState(error = "Registration failed: User with this email already exists", isLoading = false)
            } else {
                _uiState.value = UserUiState(email = email, password = password, isLoggedIn = true, isLoading = false)
            }
        }
    }

    fun logout() {
        _uiState.value = UserUiState()
    }

    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}