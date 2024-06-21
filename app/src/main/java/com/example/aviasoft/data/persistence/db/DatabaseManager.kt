package com.example.aviasoft.data.persistence.db

import com.example.aviasoft.data.network.dto.AttendantsDto
import com.example.aviasoft.data.network.dto.Barcodes
import com.example.aviasoft.data.network.dto.Currency
import com.example.aviasoft.data.network.dto.Discount
import com.example.aviasoft.data.network.dto.Merchant
import com.example.aviasoft.data.network.dto.Plane
import com.example.aviasoft.data.network.dto.Seat
import com.example.aviasoft.data.network.dto.trips.Trip
import com.example.aviasoft.domain.model.Attendant

interface DatabaseManager {

    suspend fun insertBarcodes(list: List<Barcodes>)
    suspend fun insertCurrency(list: List<Currency>)
    suspend fun insertMerchant(list: List<Merchant>)
    suspend fun insertAttendants(list: List<AttendantsDto>)
    suspend fun insertDiscountWithCategoriesAndGoods(discountList: List<Discount>)
    suspend fun insertPlanes(list: List<Plane>)
    suspend fun insertSeat(seat: Seat)
    suspend fun insertTrip(trip: Trip)
    suspend fun retrievingAttendants(): List<AttendantsDto>

}