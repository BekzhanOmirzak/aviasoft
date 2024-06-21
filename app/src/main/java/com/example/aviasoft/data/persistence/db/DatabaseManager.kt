package com.example.aviasoft.data.persistence.db

import com.example.aviasoft.data.network.dto.Attendants
import com.example.aviasoft.data.network.dto.Barcodes
import com.example.aviasoft.data.network.dto.Currency
import com.example.aviasoft.data.network.dto.Discount
import com.example.aviasoft.data.network.dto.Merchant
import com.example.aviasoft.data.network.dto.Plane
import com.example.aviasoft.data.network.dto.Seat

interface DatabaseManager {

    suspend fun insertBarcodes(list: List<Barcodes>)
    suspend fun insertCurrency(list: List<Currency>)
    suspend fun insertMerchant(list: List<Merchant>)
    suspend fun insertAttendants(list: List<Attendants>)
    suspend fun insertDiscountWithCategoriesAndGoods(discountList: List<Discount>)
    suspend fun insertPlanes(list: List<Plane>)
    suspend fun insertSeat(seat: Seat)

}