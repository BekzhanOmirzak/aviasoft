package com.example.aviasoft.data.network.dto.trips

data class Flight(
    val arrival: String,
    val category: String,
    val code: String,
    val departure: String,
    val id: String,
    val rel_title: String,
    val taxes: List<Taxe>
)