package com.example.aviasoft.data.network.dto.trips

data class Leg(
    val airplane: Airplane,
    val airplane_id: String,
    val arrival: String,
    val departure: String,
    val fiscal_machine: FiscalMachine,
    val fiscal_machine_id: String,
    val flight: Flight,
    val flight_id: String,
    val id: String,
    val modifiers: List<Modifier>,
    val rel_title: String,
    val trip_id: Int
)