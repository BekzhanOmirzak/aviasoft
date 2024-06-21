package com.example.aviasoft.data.network.dto

data class Configuration(
    val singleCurrency: Int,
    val discountsEnabled: Int,
    val customerRequired: Int,
    val printPromo: Int,
    val checkSeals: Int,
    val returnSealsRequired: Int,
    val advertisements: Int,
    val cardPaymentsEnabled: Int,
    val extCardPayments: Int,
    val goodsDeclaration: Int,
    val merchantCopy: Int,
    val printTax: Int,
    val inflightDiscount: String,
    val resetMoneyOnTransfer: Int,
    val cartItemCheckbox: Int,
    val merchantID: String,
    val merchantName: String,
    val normalFont: Int,
    val bigFont: Int,
    val lineLength: Int,
    val seatmap: Int,
    val keychainTTL: Int,
    val globalTaxes: Int,
    val skipAuth: Int,
    val forbidCash: Int,
    val cashButtons: List<Int>,
    val multiAuth: Int,
    val fltLabelCopy: Int,
    val transFinCopy: Int,
    val owmodReceipt: Int,
    val printAllSums: Int,
    val trType: Int,
    val leftoversReport: Int,
    val ignoreStartMoney: Int,
    val enableIIN: Int,
    val cardPaymentDiscount: Int,
    val discountVisa: Int,
    val discountMC: Int,
    val secondaryOWHost: String,
    val autoLSWc: Int,
    val useScreenSaver: Int,
    val IFE: Int,
    val IFEConf: String,
    val allowExpiredCards: Int,
    val defRecLang: String,
    val multiLangRec: Int,
    val markTrolleys: Int,
    val internalLang: String,
    val signatureScreen: Int,
    val merchantCopyCard: Int,
    val useControlCodes: Int,
    val perishableDiscount: String,
    val cardSingleTransAmtLimit: Int,
    val cardTripTransAmtLimit: Int,
    val cardTripTransLimit: Int,
    val pmtPhotoThreshold: Int,
    val clearanceChk: Int,
    val ITAT: Int,
    val IFEMaxWait: Int,
    val maxSeatChange: Int,
    val logLevel: Int,
    val scan2cart: Int,
    val p2pIFE: Int,
    val p2pNetmask: String,
    val p2pGw: String,
    val p2pIPRange: String,
    val pmtPosinit: Int,
    val pmtSecureiso: Int,
    val manLOReport: Int,
    val blacklistCur: List<Int>
)
