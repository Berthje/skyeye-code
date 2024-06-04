package com.howest.skyeye.ui.airport.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.howest.skyeye.apirequest.ui.APIUiStateAirportApiData
import com.howest.skyeye.apirequest.ui.APIViewModel
import com.howest.skyeye.ui.AirportApiData
import com.howest.skyeye.ui.NavigationDestination
import com.howest.skyeye.ui.map.MapView
import howest.nma.skyeye.R

object AirportDetailDestination : NavigationDestination {
    override val route = "AirportDetailScreen"
    override val title = "Airport Detail Screen"
    const val IcaoArg = "icao"
    const val airportNameArg = "airportName"
    val routeWithArgs = "$route/{$IcaoArg}/{$airportNameArg}"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AirportDetailScreen(icao: String, airportName: String, navigateTo: (route: String) -> Unit, navigateBack: () -> Unit){
    val apiViewModel: APIViewModel = viewModel()
    val apiUiState : APIUiStateAirportApiData = apiViewModel.apiUiState
    var details = emptyList<Pair<String, Any>>()
    val context = LocalContext.current
    var airportData: AirportApiData? = null
    var toast: Toast? = null

    LaunchedEffect(key1 = icao) {
        apiViewModel.getAirportData(icao)
    }
    when(apiUiState) {
        is APIUiStateAirportApiData.Success -> {
            airportData = apiUiState.data
            details = listOf(
                "ICAO Code" to airportData.ident,
                "IATA Code" to airportData.iata_code,
                "Continent" to airportData.continent,
                "Municipality" to airportData.municipality,
                "Location" to airportData.country.name,
                "Runway Count" to (airportData.runways?.size ?: 0),
                "Elevation" to airportData.elevation_ft,
                "Coordinates" to "${airportData.latitude_deg} ${airportData.longitude_deg}"
            )
        }
        is APIUiStateAirportApiData.Loading -> Text(text = "Airport information loading...", style = MaterialTheme.typography.bodyMedium)
        is APIUiStateAirportApiData.Error -> Text(text = "Couldn't find the airport data, try again later!")
    }

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
                    text = "$airportName information",
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
                Section(title = airportName) {}
            }

            items(details) { detail ->
                TitleValueComponent(title = detail.first, value = detail.second)
            }

            item {
                Section(title = "METAR") {
                    Text(
                        text = "METAR OIZB 091600Z 00000KT CAVOK 25/01 Q1014", //VARIABLE METAR
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 12.dp)
                    )
                }
            }
            item {
                Section(title = "MAP", trailing = {
                    IconButton(onClick = {
                        val gmmIntentUri = Uri.parse("geo:${airportData?.latitude_deg},${airportData?.longitude_deg}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        try {
                            context.startActivity(mapIntent)
                        } catch (e: ActivityNotFoundException) {
                            if (toast == null) {
                                toast = Toast.makeText(context, "No maps app available", Toast.LENGTH_SHORT)
                            }
                            toast?.show()
                        }
                    }) {
                        Icon(painterResource(id = R.drawable.google_maps_tile), contentDescription = "Open in Maps", modifier = Modifier.size(32.dp), tint = Color.Unspecified)
                    }
                }) {
                    if (airportData != null) {
                        MapView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            latitude = airportData.latitude_deg.toDouble(),
                            longitude = airportData.longitude_deg.toDouble(),
                            showCompass = false,
                            userInteractionEnabled = false,
                            zoomValue = 11.5,
                            selectedMapTypeSetting = "satellite",
                            showAirports = false,
                            context = LocalContext.current,
                            cameraPositionState = remember { mutableStateOf(null) },
                            navigateTo = navigateTo
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TitleValueComponent(title: String, value: Any) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp)
    )
    Text(
        text = value.toString(),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 12.dp)
    )
}

@Composable
fun Section(title: String, trailing: @Composable (() -> Unit)? = null, content: @Composable () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )
        trailing?.invoke()
    }
    HorizontalDivider(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 20.dp))
    content()
}