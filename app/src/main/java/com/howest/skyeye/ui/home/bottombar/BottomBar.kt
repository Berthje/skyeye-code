package com.howest.skyeye.ui.home.bottombar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.howest.skyeye.ui.home.modals.filter.FilterModal
import com.howest.skyeye.ui.home.modals.maptype.MapTypeModal
import com.howest.skyeye.ui.home.modals.weather.WeatherModal
import howest.nma.skyeye.R

@Composable
fun BottomBar(navigateTo: (route: String) -> Unit){
    var selectedItem by remember { mutableIntStateOf(-1) }
    var activeModal by remember { mutableStateOf("") }
    val items = listOf(
        Pair(R.drawable.settings, "Settings"),
        Pair(R.drawable.weather, "Weather"),
        Pair(R.drawable.camera, "Camera"),
        Pair(R.drawable.map, "MapType"),
        Pair(R.drawable.filter, "Filters")
    )
    NavigationBar {
        items.forEachIndexed { index, (icon, label) ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = icon), contentDescription = label) },
                label = { Text(label) },
                selected = activeModal == label,
                onClick = {
                    selectedItem = index
                    if (label in listOf("Weather", "MapType", "Filters")) {
                        activeModal = label
                    } else {
                        navigateTo(label.lowercase())
                    }
                }
            )
        }
    }
    when (activeModal) {
        "Weather" -> WeatherModal(onDismissRequest = { activeModal = "" })
        "MapType" -> MapTypeModal(onDismissRequest = { activeModal = "" })
        "Filters" -> FilterModal() { activeModal = "" }
    }
}
