package com.example.aviasoft.data.network.dto

data class Merchant(
    val id: String,
    val logo: String?,
    val address: String,
    val bin: String,
    val identityNumber: String,
    val registrationNumber: String,
    val footer: String,
    val taxId: String,
    val cashAvailable: String,
    val cardAvailable: String,
    val mid: String,
    val tid: String,
    val aboveQr: String,
    val belowQr: String
)