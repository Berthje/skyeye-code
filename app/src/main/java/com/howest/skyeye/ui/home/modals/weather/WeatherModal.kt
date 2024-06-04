package com.howest.skyeye.ui.home.modals.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.howest.skyeye.ui.AppViewModelProvider
import howest.nma.skyeye.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherModal(viewModel: WeatherViewModel = viewModel(factory = AppViewModelProvider.Factory), onDismissRequest: () -> Unit) {
    val uiState by viewModel.weatherUiState.collectAsState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = Modifier.height(700.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .height(700.dp)
                .padding(bottom = 50.dp)
        ) {
            item {
                WeatherModalItem(R.drawable.none, "No weather", "No weather layer", uiState.selectedWeatherItem == "No weather") {
                    viewModel.setWeatherItem("No weather")
                }
            }
            item {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item {
                WeatherModalItem(R.drawable.clouds, "Cloud coverage", "Global IR Satellite provides cloud cover displayed on the map.", uiState.selectedWeatherItem == "Cloud coverage") {
                    viewModel.setWeatherItem("Cloud coverage")
                }
            }
            item {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item {
                WeatherModalItem(R.drawable.rain, "Rain", "Overview of the current precipitation on our live map.", uiState.selectedWeatherItem == "Rain") {
                    viewModel.setWeatherItem("Rain")
                }
            }
        }
    }
}

@Composable
fun WeatherModalItem(imageId : Int, title: String, text : String, active : Boolean, onClick: () -> Unit) {
    Column {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = if (active) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "$title image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .padding(all = 16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .border(
                        width = if (active) 3.5.dp else 1.dp,
                        color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    )
            )
            Text(
                text = text,
                fontWeight = if (active) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}