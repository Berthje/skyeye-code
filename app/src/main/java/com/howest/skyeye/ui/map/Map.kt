package com.howest.skyeye.ui.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.Gravity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.howest.skyeye.ui.AirportMarkerData
import com.howest.skyeye.ui.airport.detail.AirportDetailDestination
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.style.expressions.Expression
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import howest.nma.skyeye.R

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    latitude: Double,
    longitude: Double,
    showCompass: Boolean = true,
    userInteractionEnabled: Boolean = true,
    zoomValue: Double = 3.5,
    selectedMapTypeSetting: String,
    context: Context,
    showAirports: Boolean = false,
    cameraPositionState: MutableState<CameraPosition?>,
    navigateTo: (route: String) -> Unit
) {
    val styleUrl = mapTypeToStyleUrl[selectedMapTypeSetting] ?: "https://api.maptiler.com/maps/basic-v2/style.json"
    val airportData = remember { mutableStateOf<List<AirportMarkerData>?>(null) }

    LaunchedEffect(Unit) {
        airportData.value = readAirportData(context)
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            Mapbox.getInstance(context)
            val mapView = com.mapbox.mapboxsdk.maps.MapView(context)
            mapView.onCreate(null)
            mapView.getMapAsync { map ->
                setupMap(
                    map,
                    styleUrl,
                    userInteractionEnabled,
                    showCompass,
                    latitude,
                    longitude,
                    zoomValue,
                    showAirports,
                    context,
                    airportData.value,
                    cameraPositionState,
                    navigateTo
                )
            }
            mapView
        },
        update = { mapView ->
            mapView.getMapAsync { map ->
                setupMap(
                    map,
                    styleUrl,
                    userInteractionEnabled,
                    showCompass,
                    latitude,
                    longitude,
                    zoomValue,
                    showAirports,
                    context,
                    airportData.value,
                    cameraPositionState,
                    navigateTo
                )
            }
        }
    )
}

fun setupMap(
    map: MapboxMap,
    styleUrl: String,
    userInteractionEnabled: Boolean,
    showCompass: Boolean,
    latitude: Double,
    longitude: Double,
    zoomValue: Double,
    showAirports: Boolean,
    context: Context,
    airportData: List<AirportMarkerData>?,
    cameraPositionState: MutableState<CameraPosition?>,
    navigateTo: (route: String) -> Unit
) {
    map.addOnCameraMoveListener {
        cameraPositionState.value = map.cameraPosition
    }
    map.setStyle("$styleUrl?key=OZkqnFxcrUbHDpJQ5a3K") { style ->
        map.uiSettings.isScrollGesturesEnabled = userInteractionEnabled
        map.uiSettings.isZoomGesturesEnabled = userInteractionEnabled
        map.uiSettings.isTiltGesturesEnabled = userInteractionEnabled
        map.uiSettings.isRotateGesturesEnabled = userInteractionEnabled
        map.uiSettings.setAttributionMargins(15, 0, 0, 15)
        map.uiSettings.isCompassEnabled = showCompass
        map.uiSettings.compassGravity = Gravity.BOTTOM or Gravity.START
        map.uiSettings.setCompassMargins(40, 0, 0, 40)
        map.uiSettings.isAttributionEnabled = false
        map.uiSettings.isLogoEnabled = false
        map.uiSettings.setCompassFadeFacingNorth(false)

        if (cameraPositionState.value == null) {
            cameraPositionState.value = CameraPosition.Builder()
                .target(LatLng(latitude, longitude))
                .zoom(zoomValue)
                .bearing(2.0)
                .build()
        }

        map.cameraPosition = cameraPositionState.value!!

        if (showAirports) {
            val drawable = ContextCompat.getDrawable(context, R.drawable.airport_marker)
            val bitmap = Bitmap.createBitmap(
                drawable!!.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            style.addImage("airport-icon", bitmap)

            val featureCollection = FeatureCollection.fromFeatures(airportData?.map {
                Feature.fromGeometry(Point.fromLngLat(it.longitude, it.latitude))
                    .also { feature ->
                        feature.addStringProperty("name", it.name)
                        feature.addStringProperty("icao", it.icao)
                    }
            } ?: emptyList())

            val source = GeoJsonSource(
                "airport-source",
                featureCollection,
                GeoJsonOptions().withCluster(true)
            )
            style.addSource(source)

            val unclustered = SymbolLayer("unclustered-points", "airport-source")
            unclustered.setProperties(
                PropertyFactory.iconImage("airport-icon"),
                PropertyFactory.iconAllowOverlap(true),
                PropertyFactory.iconIgnorePlacement(true)
            )
            style.addLayer(unclustered)

            val clustered = SymbolLayer("clustered-points", "airport-source")
            clustered.setProperties(
                PropertyFactory.iconImage("airport-icon"),
                PropertyFactory.iconAllowOverlap(true),
                PropertyFactory.iconIgnorePlacement(true)
            )
            clustered.setFilter(Expression.has("point_count"))
            style.addLayer(clustered)
        }
    }

    map.addOnMapClickListener { point ->
        val pixel = map.projection.toScreenLocation(point)
        val features = map.queryRenderedFeatures(pixel, "unclustered-points", "clustered-points")
        for (feature in features) {
            if (feature.hasProperty("cluster") && feature.getBooleanProperty("cluster")) {
                val newCameraPosition = CameraPosition.Builder()
                    .target(LatLng(point.latitude, point.longitude))
                    .zoom(map.cameraPosition.zoom + 2)
                    .build()
                map.easeCamera(CameraUpdateFactory.newCameraPosition(newCameraPosition))
                return@addOnMapClickListener true
            } else {
                // Handle click on single airport marker
                val airportICAO = feature.getStringProperty("icao")
                val airportName = feature.getStringProperty("name")

                navigateTo(AirportDetailDestination.route + "/$airportICAO/$airportName")
                return@addOnMapClickListener true
            }
        }
        false
    }
}

val mapTypeToStyleUrl = mapOf(
    "normal" to "https://api.maptiler.com/maps/basic-v2/style.json",
    "terrain" to "https://api.maptiler.com/maps/landscape/style.json",
    "satellite" to "https://api.maptiler.com/maps/satellite/style.json",
    "dark" to "https://api.maptiler.com/maps/eacc7abe-07e2-4f1f-b5ed-1c46460b8c83/style.json"
)

fun readAirportData(context: Context): List<AirportMarkerData> {
    val airportData = mutableListOf<AirportMarkerData>()
    val inputStream = context.assets.open("airports.csv")
    val reader = inputStream.bufferedReader()
    reader.readLine()
    reader.forEachLine { line ->
        val fields = line.split(",")
        val latitude = fields[4].toDoubleOrNull()
        val longitude = fields[5].toDoubleOrNull()
        val name = fields[3]
        val icao = fields[1]
        if (latitude != null && longitude != null) {
            val airportMarkerData = AirportMarkerData(name, latitude, longitude, icao)
            airportData.add(airportMarkerData)
        }
    }
    return airportData
}