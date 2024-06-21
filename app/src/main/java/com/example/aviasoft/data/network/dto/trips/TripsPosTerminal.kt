package com.example.aviasoft.data.network.dto.trips

data class TripsPosTerminal(
    val bag_id: String,
    val code: String,
    val id: String,
    val ip: String,
    val laser: String,
    val mac: String,
    val pos_terminal_id: String,
    val status: String,
    val trip_id: String
)