package com.example.aviasoft.data.network.dto

data class Seat(
    val type: String,
    val width: Int,
    val length: Double,
    val row_number: String,
    val row: List<Row>,
    val wing: String
)

data class Row(
    val type: String,
    val width: Int,
    //for the sake of  simplicity it is string
    // as this can either be an array or object
    val parameters: String
)
