package com.howest.skyeye.ui.home.modals.maptype

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun MapTypeModal(viewModel: MapTypeViewModel = viewModel(factory = AppViewModelProvider.Factory), onDismissRequest: () -> Unit) {
    val uiState by viewModel.mapTypeUiState.collectAsState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .height(530.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .height(700.dp)
                .padding(bottom = 30.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MapTypeModalItem(R.drawable.normal, "Normal", uiState.selectedMapTypeItem == "normal") {
                        viewModel.setMapTypeItem("normal")
                    }

                    MapTypeModalItem(R.drawable.terrain, "Terrain",  uiState.selectedMapTypeItem == "terrain") {
                        viewModel.setMapTypeItem("terrain")
                    }
                }
            }
            item {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MapTypeModalItem(R.drawable.satellite, "Satellite", uiState.selectedMapTypeItem == "satellite") {
                        viewModel.setMapTypeItem("satellite")
                    }
                    MapTypeModalItem(R.drawable.dark, "Dark", uiState.selectedMapTypeItem == "dark") {
                        viewModel.setMapTypeItem("dark")
                    }
                }
            }
        }
    }
}

@Composable
fun MapTypeModalItem(imageId : Int, title: String, active : Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = if (active) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                .fillMaxHeight()
        )
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "$title image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(175.dp) // Set the size of the image
                .padding(all = 16.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(
                    width = if (active) 3.5.dp else 1.dp,
                    color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = RoundedCornerShape(4.dp)
                )
        )
    }
}