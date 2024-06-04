package com.howest.skyeye.ui.aircraft.detail

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.howest.skyeye.ui.NavigationDestination
import com.howest.skyeye.ui.airport.detail.Section
import com.howest.skyeye.ui.airport.detail.TitleValueComponent
import howest.nma.skyeye.R

object AircraftDetailDestination : NavigationDestination {
    override val route = "AircraftDetailScreen"
    override val title = "Aircraft Detail Screen"
    const val aircraftTypeArg = "aircraftType"
    val routeWithArgs = "$route/{$aircraftTypeArg}"
}


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AircraftDetailScreen(aircraftType: String, navigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "$aircraftType information",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.a320),
                    contentDescription = "airplane picture",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(shape = RectangleShape),
                    contentScale = ContentScale.Crop
                )
            }

            item {
                Section(title = aircraftType) {}
            }

            val details = listOf(
                "Manufacturer" to "Airbus", //VARIABLE VALUE
                "Body" to "Narrow", //VARIABLE VALUE
                "Wing" to "Fixed Wing", //VARIABLE VALUE
                "Position" to "Low wing", //VARIABLE VALUE
                "Tail" to "Regular tail, mid set", //VARIABLE VALUE
                "WTC" to "M", //VARIABLE VALUE
                "APC" to "C", //VARIABLE VALUE
                "Type Code" to "L2J", //VARIABLE VALUE
                "ARC" to "4C", //VARIABLE VALUE
                "Engine" to "Jet", //VARIABLE VALUE
                "Engine Count" to "Multi", //VARIABLE VALUE
                "Position Engine(s)" to "Underwing mounted", //VARIABLE VALUE
                "Landing Gear" to "Tricycle retractable", //VARIABLE VALUE
            )

            items(details) { detail ->
                TitleValueComponent(title = detail.first, value = detail.second)
            }

            val technicalDetails = listOf(
                "Wing span" to "35.80 m", //VARIABLE VALUE
                "Length" to "37.57 m", //VARIABLE VALUE
                "Height" to "11.76 m", //VARIABLE VALUE
                "Powerplant" to "2x CFM56-5A1 (111kN) or\n2x CFM56-5A3 (118kN) or\n2x IAE V2500 (125kN) turbofans", //VARIABLE VALUE
                "Engine Model(s)" to "CFM International CFM56 or\nInternational Aero Engines V2500", //VARIABLE VALUE
            )

            item {
                Section(title = "Technical Data") {}
            }

            items(technicalDetails) { technicalDetail ->
                TitleValueComponent(title = technicalDetail.first, value = technicalDetail.second)
            }
        }
    }
}
