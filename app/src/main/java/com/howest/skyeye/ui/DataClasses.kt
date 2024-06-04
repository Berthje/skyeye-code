package com.howest.skyeye.ui

data class AircraftData(val name: String)

data class ManufacturerAndAircraft(
    val manufacturer: String,
    val aircraft: List<AircraftData>
)

data class AirportMarkerData(val name: String, val latitude: Double, val longitude: Double, val icao: String)

data class AirportInfo(val country: String, val ICAO: String, val name: String, val fullCountryName: String)
data class AirportData(val name: String, val ICAO: String)

data class AirportApiData(
    val ident: String,
    val type: String,
    val name: String,
    val latitude_deg: Float,
    val longitude_deg: Float,
    val elevation_ft: String,
    val continent: String,
    val iso_country: String,
    val iso_region: String,
    val municipality: String,
    val scheduled_service: String,
    val gps_code: String,
    val iata_code: String,
    val local_code: String,
    val home_link: String,
    val wikipedia_link: String,
    val keywords: String,
    val icao_code: String,
    val runways: List<Runway>,
    val freqs: List<Freq>,
    val country: Country,
    val region: Region,
    val navaids: List<Navaid>,
    val updatedAt: String,
    val station: Station
)

data class Runway(
    val id: String,
    val airport_ref: String,
    val airport_ident: String,
    val length_ft: String,
    val width_ft: String,
    val surface: String,
    val lighted: String,
    val closed: String,
    val le_ident: String,
    val le_latitude_deg: String,
    val le_longitude_deg: String,
    val le_elevation_ft: String,
    val le_heading_degT: String,
    val le_displaced_threshold_ft: String,
    val he_ident: String,
    val he_latitude_deg: String,
    val he_longitude_deg: String,
    val he_elevation_ft: String,
    val he_heading_degT: String,
    val he_displaced_threshold_ft: String,
    val he_ils: Ils,
    val le_ils: Ils
)

data class Ils(
    val freq: Float,
    val course: Int
)

data class Freq(
    val id: String,
    val airport_ref: String,
    val airport_ident: String,
    val type: String,
    val description: String,
    val frequency_mhz: String
)

data class Country(
    val id: String,
    val code: String,
    val name: String,
    val continent: String,
    val wikipedia_link: String,
    val keywords: String
)

data class Region(
    val id: String,
    val code: String,
    val local_code: String,
    val name: String,
    val continent: String,
    val iso_country: String,
    val wikipedia_link: String,
    val keywords: String
)

data class Navaid(
    val id: String,
    val filename: String,
    val ident: String,
    val name: String,
    val type: String,
    val frequency_khz: String,
    val latitude_deg: String,
    val longitude_deg: String,
    val elevation_ft: String,
    val iso_country: String,
    val dme_frequency_khz: String,
    val dme_channel: String,
    val dme_latitude_deg: String,
    val dme_longitude_deg: String,
    val dme_elevation_ft: String,
    val slaved_variation_deg: String,
    val magnetic_variation_deg: String,
    val usageType: String,
    val power: String,
    val associated_airport: String
)

data class Station(
    val icao_code: String,
    val distance: Double
)