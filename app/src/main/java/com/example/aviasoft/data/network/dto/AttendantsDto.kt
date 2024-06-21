package com.example.aviasoft.data.network.dto

import com.google.gson.annotations.SerializedName

data class AttendantsDto(
    val id: String,
    val code: String,
    val name: String,
    @SerializedName("base_id")
    val baseId: String,
    val type: String,
    val email: String,
    val language: String
)