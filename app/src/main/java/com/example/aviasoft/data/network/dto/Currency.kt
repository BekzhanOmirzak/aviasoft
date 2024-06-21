package com.example.aviasoft.data.network.dto

import com.google.gson.annotations.SerializedName

data class Currency(
    val id: String,
    val title: String,
    val code: String,
    val standard: String,
    @SerializedName("pos_available")
    val posAvailable: String,
    @SerializedName("pos_standard")
    val posStandard: String,
    val isonum: String
)