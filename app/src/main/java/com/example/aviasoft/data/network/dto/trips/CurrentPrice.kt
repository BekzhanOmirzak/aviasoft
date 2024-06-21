package com.example.aviasoft.data.network.dto.trips

data class CurrentPrice(
    val currency_id: String,
    val good_id: String,
    val id: String,
    val price: String,
    val ptype: String,
    val type: String
)