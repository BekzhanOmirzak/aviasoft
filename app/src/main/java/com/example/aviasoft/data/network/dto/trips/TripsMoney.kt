package com.example.aviasoft.data.network.dto.trips

data class TripsMoney(
    val amount: String,
    val bag_id: String,
    val currency_code: String,
    val currency_id: String,
    val id: String,
    val isonum: String,
    val pos_available: String,
    val pos_standard: String,
    val standard: String,
    val title: String
)