package com.howest.skyeye.ui.user


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.howest.skyeye.ui.AppViewModelProvider
import com.howest.skyeye.ui.NavigationDestination
import com.howest.skyeye.ui.home.HomeDestination
import com.howest.skyeye.ui.theme.ThemeViewModel
import howest.nma.skyeye.R

object ForgotPasswordDestination : NavigationDestination {
    override val route: String = "forgotPassword"
    override val title: String = "Forgot Password"
}

@Composable
fun ForgotPasswordScreen(navigateTo: (route: String) -> Unit, viewModel: ThemeViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val greeting = "Forgot your password?"
    val mainUiState by viewModel.themeUiState.collectAsState()
    val isDarkMode = mainUiState.isDarkMode

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        ) {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)) {
                IconButton(onClick = { navigateTo(HomeDestination.route)}) {
                    Icon(
                        Icons.Rounded.Close,
                        contentDescription = "close",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            val logo = if (isDarkMode) R.drawable.logo_skyeye else R.drawable.logo_skyeye_light
            Image(
                painter = painterResource(id = logo),
                contentDescription = null,
                modifier = Modifier.size(140.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.64f)
                    .padding(0.dp, 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = greeting,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)
                )
                Text(
                    text = "Enter your email to request a new password",
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(0.75f)
            ) {
                var email by remember { mutableStateOf("") }
                fun isValidEmail(email: String): Boolean {
                    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex()
                    return emailRegex.matches(email)
                }

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    label = { Text("Email") },
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .fillMaxWidth()
                )

                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(5.dp),
                    enabled = isValidEmail(email),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row {
                        Text(text = "Request new password")
                    }
                }
            }
        }
    }
}
