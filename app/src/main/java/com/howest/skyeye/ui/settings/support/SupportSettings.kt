package com.howest.skyeye.ui.settings.support

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.howest.skyeye.ui.NavigationDestination
import com.howest.skyeye.ui.settings.SettingsTopBar
import howest.nma.skyeye.R

object SupportDestination : NavigationDestination {
    override val route: String = "support"
    override val title: String = "Support"
}

@Composable
fun SupportSettingsScreen(navigateBack: () -> Unit) {
    var isBackgroundLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isBackgroundLoaded = true
    }

    if (isBackgroundLoaded) {
        Column (
            modifier = Modifier.fillMaxSize()
        ) {
            SettingsTopBar(navigateBack, "Support")
            SupportForm()
        }
    }
}

@Composable
fun SupportForm() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isFormSubmitted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 50.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isFormSubmitted) {
            Text(
                text = "Thank you for your message, we will get back to you as soon as possible!",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 70.dp)
            )
            Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = "Message sent successfully",
                modifier = Modifier.size(150.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
        } else {
            SupportTexts()
            SupportTextField(value = name, onValueChange = { name = it }, label = "Name")
            SupportTextField(value = email, onValueChange = { email = it }, label = "Email")
            SupportTextField(value = message, onValueChange = { message = it }, label = "message" , minLines = 5)
            SupportButton(isEnabled = name.isNotBlank() && email.isNotBlank() && message.isNotBlank()) {
                isFormSubmitted = true
            }
            SupportEmail()
        }
    }
}

@Composable
fun SupportTexts() {
    Text(
        text = "Problem or suggestion?",
        fontSize = 26.sp,
    )
    Text(
        text = "Fill in the form below and we will get back to you as soon as possible!",
        fontSize = 18.sp,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp)
    )
}

@Composable
fun SupportTextField(value: String, onValueChange: (String) -> Unit, label: String, minLines: Int = 1) {
    val keyboardType = if (label == "Email") KeyboardType.Email else KeyboardType.Text
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        singleLine = minLines == 1,
        minLines = minLines,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        label = { Text(label) }
    )
}

@Composable
fun SupportButton(isEnabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.extraSmall,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        enabled = isEnabled
    ) {
        Text(text = "Send")
    }
}
@Composable
fun SupportEmail() {
    val context = LocalContext.current
    var toast: Toast? = null
    val email = stringResource(id = R.string.support_email)

    Text(
        text = "You can also reach us by email at",
        fontSize = 18.sp,
    )
    Text(
        text = email,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        fontSize = 20.sp,
        style = TextStyle(textDecoration = TextDecoration.Underline),
        modifier = Modifier.clickable {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
            }
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                if (toast == null) {
                    toast = Toast.makeText(context, "No email app available", Toast.LENGTH_SHORT)
                    toast?.setGravity(Gravity.TOP, 0, 0)
                }
                toast?.show()
            }

        }
    )
}