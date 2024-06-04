package com.howest.skyeye.ui.airport

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.howest.skyeye.ui.AirportInfo
import com.howest.skyeye.ui.NavigationDestination
import com.howest.skyeye.ui.airport.detail.AirportDetailDestination

object SeeAllAirportsDestination : NavigationDestination {
    override val route: String = "seeAllAirports"
    override val title: String = "See All Airports"
}

@Composable
fun AirportsScreen(navigateTo: (route: String) -> Unit, navigateBack: () -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    // Move the isExpanded variable here
    var expandedCountries by remember { mutableStateOf(setOf<String>()) }

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
                    text = "Airports information",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }

        var searchText by remember { mutableStateOf("") }

        // Search bar
        OutlinedTextField(
            value = searchText,
            placeholder = {
                Text(
                    "EBBR",
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                )
            },
            onValueChange = { searchText = it},
            label = { Text("Search ICAO code or airport name") },
            leadingIcon = {
                Icon(Icons.Rounded.Search, contentDescription = "Filter")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    // Here we have to filter the text etc.
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        AirportList(navigateTo, expandedCountries, searchText) { country -> expandedCountries = toggleCountry(expandedCountries, country) }
    }
}

@Composable
fun AirportList(navigateTo: (route: String) -> Unit, expandedCountries: Set<String>, searchText: String, onToggle: (String) -> Unit) {
    val context = LocalContext.current
    val airportData = remember { mutableStateOf<List<AirportInfo>?>(null) }

    LaunchedEffect(Unit) {
        airportData.value = readAirportInfo(context)
    }

    val airportsByCountry = airportData.value?.groupBy { it.country }

    // Filter countries based on search text
    val filteredCountries = airportsByCountry?.filterKeys { country ->
        country.contains(searchText, ignoreCase = true) ||
                airportsByCountry[country]?.any {
                    it.ICAO.contains(searchText, ignoreCase = true) ||
                            it.name.contains(searchText, ignoreCase = true)
                } == true
    }


    LazyColumn {
        filteredCountries?.forEach { (country, airports) ->
            airports.firstOrNull()?.let { firstAirport ->
                item {
                    CountryItem(firstAirport, onClick = { onToggle(country) }, isExpanded = expandedCountries.contains(country))
                }

                if (expandedCountries.contains(country)) {
                    val filteredAirports = if (country.contains(searchText, ignoreCase = true)) {
                        airports // If the country name matches the search text, show all airports
                    } else {
                        airports.filter { it.ICAO.contains(searchText, ignoreCase = true) || it.name.contains(searchText, ignoreCase = true) }
                    }
                    items(filteredAirports) { airport ->
                        AirportItem(navigateTo, airport.name, airport.ICAO)
                    }
                }
            }
        }
    }
}

@Composable
fun CountryItem(airportInfo: AirportInfo, onClick: () -> Unit, isExpanded: Boolean) {
    // Clickable row for the country
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = airportInfo.country, style = MaterialTheme.typography.labelSmall)

        // Content (Country name and toggle icon)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = airportInfo.fullCountryName, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Toggle",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun AirportItem(navigateTo: (route: String) -> Unit, airportName: String, ICAO: String) {
    // Each airport item under the country
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(start = 16.dp) // Add left padding for child items
            .clickable {
                navigateTo(AirportDetailDestination.route + "/$ICAO/$airportName")
            },
    ) {
        // Label on top
        Text(text = ICAO, style = MaterialTheme.typography.labelSmall)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = airportName, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

private fun toggleCountry(expandedCountries: Set<String>, country: String): Set<String> {
    return if (expandedCountries.contains(country)) {
        expandedCountries - country
    } else {
        expandedCountries + country
    }
}

fun readAirportInfo(context: Context): List<AirportInfo> {
    val airportData = mutableListOf<AirportInfo>()
    val inputStream = context.assets.open("airports.csv")
    val reader = inputStream.bufferedReader()
    reader.readLine() // Skip the header line
    reader.forEachLine { line ->
        val fields = line.split(",")
        val country = fields[8] // iso_country field
        val icao = fields[1] // ident field
        val name = fields[3] // name field
        val fullCountryName = fields[18]
        airportData.add(AirportInfo(country, icao, name, fullCountryName))
    }
    return airportData
}

