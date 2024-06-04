package com.howest.skyeye.ui.settings.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.howest.skyeye.ui.NavigationDestination
import com.howest.skyeye.ui.settings.SettingsTopBar

object AboutDestination : NavigationDestination {
    override val route: String = "about"
    override val title: String = "About"
}

@Composable
fun AboutSettingsScreen(navigateBack: () -> Unit) {
    var isBackgroundLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isBackgroundLoaded = true
    }

    if (isBackgroundLoaded) {
        val items = listOf(
            "Build version",
            "Privacy policy",
            "Content policy",
            "User Agreement"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SettingsTopBar(navigateBack, "About settings")
            AboutSettingsItems(items)
        }
    }
}

@Composable
fun AboutSettingsItems(items: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 20.dp)
    ) {

        items.forEachIndexed { index, item ->
            AboutSettingsItem(item)
            if (index < items.size - 1) {
                Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f), thickness = 1.dp)
            }
        }
    }
}

@Composable
fun AboutSettingsItem(item: String) {
    val isClickable = item != "Build version"
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isClickable) {
                    Modifier.clickable(onClick = {
                        //navController.navigate(item.lowercase())
                    })
                } else Modifier
            )
    ) {
        SettingsItemRow(item = item, isClickable = isClickable)
    }
}

@Composable
fun SettingsItemRow(item: String, isClickable: Boolean) {
    Row(
        modifier = Modifier
            .padding(vertical = 24.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (item == "Build version") {
            BuildVersionItem(item = item)
        } else {
            RegularSettingsItem(item = item, isClickable = isClickable)
        }
    }
}

@Composable
fun BuildVersionItem(item: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 20.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
        )
        Text(
            text = "1.0.0",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.End,
            fontSize = 20.sp,
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp)

        )
    }
}

@Composable
fun RegularSettingsItem(item: String, isClickable: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 20.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
        )
        if (isClickable) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = "End Icon",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(30.dp).padding(top = 5.dp)
            )
        }
    }
}