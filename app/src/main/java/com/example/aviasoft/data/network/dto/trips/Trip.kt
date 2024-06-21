package com.example.aviasoft.data.network.dto.trips

data class Trip(
    val airplane: Airplane,
    val airplane_id: Int,
    val bags: List<Bag>,
    val cash_allowed: String,
    val goods: List<Good>,
    val id: String,
    val legs: List<Leg>,
    val server_time: String,
    val status: String,
    val trips_money: List<TripsMoney>,
    val trips_pos_terminals: List<TripsPosTerminal>,
    val trips_products: List<TripsProduct>,
    val trolleys_count: String
)