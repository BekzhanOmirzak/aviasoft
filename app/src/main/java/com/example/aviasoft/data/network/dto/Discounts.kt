package com.example.aviasoft.data.network.dto

data class Discount(
    val id: String,
    val type: String?,
    val code: String?,
    val value: String?,
    val minimum_amount: String?,
    val minimum_quantity: String?,
    val limit_total: String?,
    val limit_one_customer: String,
    val start_date: String?,
    val expire_date: String?,
    val is_enabled: String,
    val discounts_categories: List<DiscountCategory>?,
    val discounts_goods: List<DiscountGood>?
)

data class DiscountCategory(
    val discount_id: String,
    val category_id: String
)

data class DiscountGood(
    val discount_id: String,
    val good_id: String
)