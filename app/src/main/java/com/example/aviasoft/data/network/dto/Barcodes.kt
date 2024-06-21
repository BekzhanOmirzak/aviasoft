package com.example.aviasoft.data.network.dto

import com.google.gson.annotations.SerializedName

data class Barcodes(
    val id: String,
    val code: String,
    @SerializedName("product_id")
    val productId: String
)