package com.example.aviasoft.data.repository

import android.util.Log
import com.example.aviasoft.data.mapper.toAttendant
import com.example.aviasoft.data.network.client.SkyInfoClient
import com.example.aviasoft.data.network.dto.AttendantsDto
import com.example.aviasoft.data.network.dto.Barcodes
import com.example.aviasoft.data.network.dto.Configuration
import com.example.aviasoft.data.network.dto.Currency
import com.example.aviasoft.data.network.dto.Discount
import com.example.aviasoft.data.network.dto.Merchant
import com.example.aviasoft.data.network.dto.Plane
import com.example.aviasoft.data.network.dto.Row
import com.example.aviasoft.data.network.dto.Seat
import com.example.aviasoft.data.network.dto.trips.Trip
import com.example.aviasoft.data.persistence.db.DatabaseManager
import com.example.aviasoft.data.persistence.pref.PreferenceManager
import com.example.aviasoft.domain.respositotory.SkyInfoRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class SkyInfoInteractorImpl(
    private val skyInfoClient: SkyInfoClient,
    private val databaseManager: DatabaseManager,
    private val preferenceManager: PreferenceManager
) : SkyInfoRepository {

    override suspend fun retrievingAttendantsList() =
        databaseManager.retrievingAttendants().map { it.toAttendant() }

    override suspend fun downloadingSkyInfo() {

        //As the api documentation is not provided, I am expecting all properties is not null
        getConfig()
        gettingCurrencies()
        getTripsShow()
        getDiscounts()
        getSeatMap()
        //getGoodsTaxes is null
        gettingMerchants()
        //getTblPaginated no need
        gettingPlanes()
        gettingBarcodes()

    }

    override suspend fun downloadAttendants() {
        gettingDicts()
    }

    private suspend fun getTripsShow() {
        skyInfoClient.makePostNetworkCall(
            methodName = "trips.show",
            requestParam = hashMapOf(
                "company" to "wa",
                "id" to "10"
            )
        )?.let { result ->
            val data =
                JSONObject(result).getJSONObject("result").getJSONObject("data")
            val type = object : TypeToken<Trip>() {}.type

            val trip = Gson().fromJson<Trip>(data.toString(), type)
            Log.d("slkfjd23ds", "getting trip: $trip")
            databaseManager.insertTrip(trip)
        }
    }

    private suspend fun getSeatMap() {
        val planeId = "50"
        skyInfoClient.makePostNetworkCall(
            methodName = "getSeatMap",
            requestParam = hashMapOf(
                "company" to "wa",
                "planes_ids" to planeId
            )
        )?.let { result ->
            val data =
                JSONObject(result).getJSONObject("result").getJSONObject("data").getJSONArray("50")

            Log.d("sljdfl23", "getSeatMap: seats : $data")
            for (i in 0 until data.length()) {
                val jsonSeat = data.getJSONObject(i)
                val type = jsonSeat.getString("type")
                val width = jsonSeat.getInt("width")
                val length = jsonSeat.getDouble("length")
                val rowNumber = jsonSeat.getString("row_number")
                val row = jsonSeat.getJSONArray("row")
                val wing = jsonSeat.getString("wing")
                val rows = mutableListOf<Row>()
                for (j in 0 until row.length()) {
                    val curRow = row.getJSONObject(j)
                    val rowType = curRow.getString("type")
                    val rowWidth = curRow.getInt("width")
                    val paramsString = curRow.get("parameters").toString()
                    rows.add(Row(rowType, rowWidth, paramsString))
                }
                val seat = Seat(
                    type = type,
                    width = width,
                    length = length,
                    row_number = rowNumber,
                    row = rows,
                    wing = wing
                )

                databaseManager.insertSeat(seat)
            }
        }
    }

//    "id": "2",
//    "code": "P4-KBA"

    private suspend fun gettingPlanes() {
        skyInfoClient.makePostNetworkCall(
            methodName = "getPlanes",
            requestParam = hashMapOf(
                "company" to "wa",
                "pos_id" to "105",
                "trip_id" to "10"
            )
        )?.let { result ->
            val data = JSONObject(result).getJSONObject("result").getJSONArray("data")
            val type = object : TypeToken<List<Plane>>() {}.type

            val planes = Gson().fromJson<List<Plane>>(data.toString(), type)
            Log.d("slkfjd23ds", "gettingPlanes: $planes")
            databaseManager.insertPlanes(planes)
        }
    }

    private suspend fun getConfig() {
        skyInfoClient.makePostNetworkCall(
            methodName = "getConfig",
            requestParam = hashMapOf("company" to "wa")
        )?.let { result ->
            val data = JSONObject(result).getJSONObject("result").getJSONObject("data")
            val type = object : TypeToken<Configuration>() {}.type

            val configuration = Gson().fromJson<Configuration>(data.toString(), type)
            Log.d("3243lkjldsf", "getConfig: Configuration : $configuration")
            preferenceManager.saveString(
                PreferenceManager.CONFIGURATION_KEY,
                Gson().toJson(configuration)
            )
        }
    }

    private suspend fun gettingBarcodes() {
        skyInfoClient.makePostNetworkCall(
            methodName = "getBarcodes",
            requestParam = hashMapOf("company" to "wa")
        )?.let { result ->
            val data = JSONObject(result).getJSONObject("result").getJSONArray("data")
            val type = object : TypeToken<List<Barcodes>>() {}.type

            val barcodes = Gson().fromJson<List<Barcodes>>(data.toString(), type)

            Log.d("4dlfj232", "Barcodes List : ${barcodes.toList()}")
            databaseManager.insertBarcodes(barcodes)
        }
    }

    private suspend fun gettingDicts() {
        skyInfoClient.makePostNetworkCall(
            methodName = "getDicts",
            requestParam = hashMapOf("company" to "wa")
        )?.let { result ->
            val data = JSONObject(result).getJSONObject("result").getJSONObject("data")
                .getJSONArray("getAttendants")
            val type = object : TypeToken<List<AttendantsDto>>() {}.type

            val attendants = Gson().fromJson<List<AttendantsDto>>(data.toString(), type)

            Log.d("4dlfj232", "Attendants List : ${attendants.toList()}")
            databaseManager.insertAttendants(attendants)
        }
    }


    private suspend fun gettingMerchants() {
        skyInfoClient.makePostNetworkCall(
            methodName = "getMerchants",
            requestParam = hashMapOf("company" to "wa")
        )?.let { result ->
            val data = JSONObject(result).getJSONObject("result").getJSONObject("data")
                .getJSONArray("getMerchants")
            val type = object : TypeToken<List<Merchant>>() {}.type

            val merchants = Gson().fromJson<List<Merchant>>(data.toString(), type)
            Log.d("ljsdls3224s1", "Merchants List : ${merchants.toList()}")
            databaseManager.insertMerchant(merchants)
        }
    }

    private suspend fun gettingCurrencies() {
        skyInfoClient.makePostNetworkCall(
            methodName = "getCurrencies",
            requestParam = hashMapOf("company" to "wa")
        )?.let { result ->
            val data = JSONObject(result).getJSONObject("result").getJSONObject("data")
                .getJSONArray("getCurrencies")
            val type = object : TypeToken<List<Currency>>() {}.type
            val currencies = Gson().fromJson<List<Currency>>(data.toString(), type)
            Log.d("adlsjkwec23", "Currencies List: ${currencies.toList()}")
            databaseManager.insertCurrency(currencies)
        }
    }


    private suspend fun getDiscounts() {
        skyInfoClient.makePostNetworkCall(
            methodName = "getDiscounts",
            requestParam = hashMapOf("company" to "wa")
        )?.let { result ->
            val data = JSONObject(result).getJSONObject("result").getJSONArray("data")
            val type = object : TypeToken<List<Discount>>() {}.type

            val discountList = Gson().fromJson<List<Discount>>(data.toString(), type)
            Log.d("34lfdjl34", "getDiscount: DiscountList : $discountList")
            databaseManager.insertDiscountWithCategoriesAndGoods(discountList)
        }
    }


}