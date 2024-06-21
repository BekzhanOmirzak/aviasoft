package com.example.aviasoft.data.network.dto.trips

data class Good(
    val category: String,
    val category_id: String,
    val comboProducts: List<List<Int>>,
    val current_prices: List<CurrentPrice>,
    val id: String,
    val ignore_cat: String,
    val image_code: String,
    val image_id: String,
    val is_hidden: String,
    val merchant_id: String,
    val product_id: String,
    val tag_id: String,
    val title: String
)