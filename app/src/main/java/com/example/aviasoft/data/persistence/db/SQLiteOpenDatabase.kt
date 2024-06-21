package com.example.aviasoft.data.persistence.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.aviasoft.data.network.dto.AttendantsDto
import com.example.aviasoft.data.network.dto.Barcodes
import com.example.aviasoft.data.network.dto.Currency
import com.example.aviasoft.data.network.dto.Discount
import com.example.aviasoft.data.network.dto.Merchant
import com.example.aviasoft.data.network.dto.Plane
import com.example.aviasoft.data.network.dto.Seat
import com.example.aviasoft.data.network.dto.trips.Trip
import com.example.aviasoft.domain.model.Attendant

class SQLiteOpenDatabase(
    context: Context
) : SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    DB_VERSION
), DatabaseManager {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_BARCODES_TABLE = ("CREATE TABLE $TABLE_BARCODES ("
                + "$COLUMN_BARCODE_ID TEXT PRIMARY KEY, "
                + "$COLUMN_BARCODE_CODE TEXT NOT NULL, "
                + "$COLUMN_BARCODE_PRODUCT_ID TEXT NOT NULL)")

        val CREATE_ATTENDANTS_TABLE = ("CREATE TABLE $TABLE_ATTENDANTS ("
                + "$COLUMN_ATTENDANT_ID TEXT PRIMARY KEY, "
                + "$COLUMN_ATTENDANT_CODE TEXT NOT NULL, "
                + "$COLUMN_ATTENDANT_NAME TEXT NOT NULL, "
                + "$COLUMN_ATTENDANT_BASE_ID TEXT NOT NULL, "
                + "$COLUMN_ATTENDANT_TYPE TEXT NOT NULL, "
                + "$COLUMN_ATTENDANT_EMAIL TEXT NOT NULL, "
                + "$COLUMN_ATTENDANT_LANGUAGE TEXT NOT NULL)")

        val CREATE_CURRENCY_TABLE = ("CREATE TABLE $TABLE_CURRENCY ("
                + "$COLUMN_CURRENCY_ID TEXT PRIMARY KEY, "
                + "$COLUMN_CURRENCY_TITLE TEXT NOT NULL, "
                + "$COLUMN_CURRENCY_CODE TEXT NOT NULL, "
                + "$COLUMN_CURRENCY_STANDARD TEXT NOT NULL, "
                + "$COLUMN_CURRENCY_POS_AVAILABLE TEXT NOT NULL, "
                + "$COLUMN_CURRENCY_POS_STANDARD TEXT NOT NULL, "
                + "$COLUMN_CURRENCY_ISONUM TEXT NOT NULL)")

        val CREATE_MERCHANT_TABLE = ("CREATE TABLE $TABLE_MERCHANT ("
                + "$COLUMN_MERCHANT_ID TEXT PRIMARY KEY, "
                + "$COLUMN_MERCHANT_LOGO TEXT, "
                + "$COLUMN_MERCHANT_ADDRESS TEXT NOT NULL, "
                + "$COLUMN_MERCHANT_BIN TEXT NOT NULL, "
                + "$COLUMN_MERCHANT_IDENTITY_NUMBER TEXT NOT NULL, "
                + "$COLUMN_MERCHANT_REGISTRATION_NUMBER TEXT NOT NULL, "
                + "$COLUMN_MERCHANT_FOOTER TEXT NOT NULL, "
                + "$COLUMN_MERCHANT_TAX_ID TEXT NOT NULL, "
                + "$COLUMN_MERCHANT_CASH_AVAILABLE TEXT NOT NULL, "
                + "$COLUMN_MERCHANT_CARD_AVAILABLE TEXT NOT NULL, "
                + "$COLUMN_MERCHANT_MID TEXT NOT NULL, "
                + "$COLUMN_MERCHANT_TID TEXT NOT NULL, "
                + "$COLUMN_MERCHANT_ABOVE_QR TEXT NOT NULL, "
                + "$COLUMN_MERCHANT_BELOW_QR TEXT NOT NULL)")


        val SQL_CREATE_DISCOUNTS =
            "CREATE TABLE $TABLE_DISCOUNTS (" +
                    "$COLUMN_DISCOUNT_ID TEXT PRIMARY KEY," +
                    "$COLUMN_TYPE TEXT," +
                    "$COLUMN_CODE TEXT," +
                    "$COLUMN_VALUE TEXT," +
                    "$COLUMN_MINIMUM_AMOUNT TEXT," +
                    "$COLUMN_MINIMUM_QUANTITY TEXT," +
                    "$COLUMN_LIMIT_TOTAL TEXT," +
                    "$COLUMN_LIMIT_ONE_CUSTOMER TEXT," +
                    "$COLUMN_START_DATE TEXT," +
                    "$COLUMN_EXPIRE_DATE TEXT," +
                    "$COLUMN_IS_ENABLED TEXT)"

        val SQL_CREATE_DISCOUNTS_CATEGORIES =
            "CREATE TABLE $TABLE_DISCOUNTS_CATEGORIES (" +
                    "$COLUMN_DISCOUNT_ID TEXT," +
                    "$COLUMN_CATEGORY_ID TEXT," +
                    "FOREIGN KEY($COLUMN_DISCOUNT_ID) REFERENCES $TABLE_DISCOUNTS($COLUMN_DISCOUNT_ID))"

        val SQL_CREATE_DISCOUNTS_GOODS =
            "CREATE TABLE $TABLE_DISCOUNTS_GOODS (" +
                    "$COLUMN_DISCOUNT_ID TEXT," +
                    "$COLUMN_GOOD_ID TEXT," +
                    "FOREIGN KEY($COLUMN_DISCOUNT_ID) REFERENCES $TABLE_DISCOUNTS($COLUMN_DISCOUNT_ID))"


        val SQL_CREATE_PLANES =
            "CREATE TABLE $TABLE_PLANES (" +
                    "$COLUMN_PLANE_ID TEXT PRIMARY KEY," +
                    "$COLUMN_PLANE_CODE TEXT)"


        val SQL_CREATE_SEATS =
            "CREATE TABLE $TABLE_SEATS (" +
                    "$COLUMN_SEAT_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_SEAT_TYPE TEXT," +
                    "$COLUMN_SEAT_WIDTH INTEGER," +
                    "$COLUMN_SEAT_LENGTH REAL," +
                    "$COLUMN_SEAT_ROW_NUMBER TEXT," +
                    "$COLUMN_SEAT_WING TEXT)"

        val SQL_CREATE_ROWS =
            "CREATE TABLE $TABLE_ROWS (" +
                    "$COLUMN_ROW_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_ROW_TYPE TEXT," +
                    "$COLUMN_ROW_WIDTH INTEGER," +
                    "$COLUMN_ROW_SEAT_ID INTEGER," +
                    "$COLUMN_ROW_PARAMETERS TEXT," +
                    "FOREIGN KEY($COLUMN_ROW_SEAT_ID) REFERENCES $TABLE_SEATS($COLUMN_SEAT_ID))"

        val SQL_CREATE_TRIPS = """
    CREATE TABLE $TABLE_TRIPS (
        $COLUMN_TRIP_ID TEXT PRIMARY KEY,
        $COLUMN_TRIP_AIRPLANE_ID INTEGER,
        $COLUMN_TRIP_CASH_ALLOWED TEXT,
        $COLUMN_TRIP_SERVER_TIME TEXT,
        $COLUMN_TRIP_STATUS TEXT,
        $COLUMN_TRIP_TROLLEYS_COUNT TEXT
    )
""".trimIndent()

        val SQL_CREATE_BAGS = """
    CREATE TABLE $TABLE_BAGS (
        $COLUMN_BAG_ID TEXT PRIMARY KEY,
        $COLUMN_BAG_CAPACITY TEXT,
        $COLUMN_BAG_CODE TEXT,
        $COLUMN_BAG_STATUS TEXT,
        $COLUMN_BAG_TRIP_ID TEXT,
        FOREIGN KEY($COLUMN_BAG_TRIP_ID) REFERENCES $TABLE_TRIPS($COLUMN_TRIP_ID)
    )
""".trimIndent()

        val SQL_CREATE_GOODS = """
    CREATE TABLE $TABLE_GOODS (
        $COLUMN_GOODS_ID TEXT PRIMARY KEY,
        $COLUMN_GOOD_CATEGORY TEXT,
        $COLUMN_GOOD_CATEGORY_ID TEXT,
        $COLUMN_GOOD_IMAGE_CODE TEXT,
        $COLUMN_GOOD_IMAGE_ID TEXT,
        $COLUMN_GOOD_IS_HIDDEN TEXT,
        $COLUMN_GOOD_MERCHANT_ID TEXT,
        $COLUMN_GOOD_PRODUCT_ID TEXT,
        $COLUMN_GOOD_TAG_ID TEXT,
        $COLUMN_GOOD_TITLE TEXT
    )
""".trimIndent()

        val SQL_CREATE_CURRENT_PRICES = """
    CREATE TABLE $TABLE_CURRENT_PRICES (
        $COLUMN_CURRENT_PRICE_ID TEXT PRIMARY KEY,
        $COLUMN_CURRENT_PRICE_CURRENCY_ID TEXT,
        $COLUMN_CURRENT_PRICE_GOOD_ID TEXT,
        $COLUMN_CURRENT_PRICE TEXT,
        $COLUMN_CURRENT_PRICE_PTYPE TEXT,
        $COLUMN_CURRENT_PRICE_TYPE TEXT,
        FOREIGN KEY($COLUMN_CURRENT_PRICE_GOOD_ID) REFERENCES $TABLE_GOODS($COLUMN_GOOD_ID)
    )
""".trimIndent()

        val SQL_CREATE_LEGS = """
    CREATE TABLE $TABLE_LEGS (
        $COLUMN_LEG_ID TEXT PRIMARY KEY,
        $COLUMN_LEG_AIRPLANE_ID TEXT,
        $COLUMN_LEG_ARRIVAL TEXT,
        $COLUMN_LEG_DEPARTURE TEXT,
        $COLUMN_LEG_FISCAL_MACHINE_ID TEXT,
        $COLUMN_LEG_FLIGHT_ID TEXT,
        $COLUMN_LEG_REL_TITLE TEXT,
        $COLUMN_LEG_TRIP_ID TEXT,
        FOREIGN KEY($COLUMN_LEG_TRIP_ID) REFERENCES $TABLE_TRIPS($COLUMN_TRIP_ID)
    )
""".trimIndent()

        val SQL_CREATE_FISCAL_MACHINES = """
    CREATE TABLE $TABLE_FISCAL_MACHINES (
        $COLUMN_FISCAL_MACHINE_ID TEXT PRIMARY KEY,
        $COLUMN_FISCAL_MACHINE_ABOVE_QR TEXT,
        $COLUMN_FISCAL_MACHINE_ADDRESS TEXT,
        $COLUMN_FISCAL_MACHINE_BELOW_QR TEXT,
        $COLUMN_FISCAL_MACHINE_BIN TEXT,
        $COLUMN_FISCAL_MACHINE_IDENTITY_NUMBER TEXT,
        $COLUMN_FISCAL_MACHINE_REGISTRATION_NUMBER TEXT,
        $COLUMN_FISCAL_MACHINE_TITLE TEXT
    )
""".trimIndent()

        val SQL_CREATE_FLIGHTS = """
    CREATE TABLE $TABLE_FLIGHTS (
        $COLUMN_FLIGHT_ID TEXT PRIMARY KEY,
        $COLUMN_FLIGHT_ARRIVAL TEXT,
        $COLUMN_FLIGHT_CATEGORY TEXT,
        $COLUMN_FLIGHT_CODE TEXT,
        $COLUMN_FLIGHT_DEPARTURE TEXT,
        $COLUMN_FLIGHT_REL_TITLE TEXT
    )
""".trimIndent()

        val SQL_CREATE_TAXES = """
    CREATE TABLE $TABLE_TAXES (
        $COLUMN_TAX_ID TEXT PRIMARY KEY,
        $COLUMN_TAX_FLIGHT_ID TEXT,
        $COLUMN_TAX_PERCENT TEXT,
        $COLUMN_TAX_TITLE TEXT,
        FOREIGN KEY($COLUMN_TAX_FLIGHT_ID) REFERENCES $TABLE_FLIGHTS($COLUMN_FLIGHT_ID)
    )
""".trimIndent()

        val SQL_CREATE_MODIFIERS = """
    CREATE TABLE $TABLE_MODIFIERS (
        $COLUMN_MODIFIER_ID TEXT PRIMARY KEY,
        $COLUMN_MODIFIER_FLIGHT_ID TEXT,
        $COLUMN_MODIFIER_GOOD_ID TEXT,
        $COLUMN_MODIFICATOR TEXT,
        FOREIGN KEY($COLUMN_MODIFIER_FLIGHT_ID) REFERENCES $TABLE_FLIGHTS($COLUMN_FLIGHT_ID),
        FOREIGN KEY($COLUMN_MODIFIER_GOOD_ID) REFERENCES $TABLE_GOODS($COLUMN_GOOD_ID)
    )
""".trimIndent()

        val SQL_CREATE_TRIPS_MONEY = """
    CREATE TABLE $TABLE_TRIPS_MONEY (
        $COLUMN_TRIPS_MONEY_ID TEXT PRIMARY KEY,
        $COLUMN_TRIPS_MONEY_AMOUNT TEXT,
        $COLUMN_TRIPS_MONEY_BAG_ID TEXT,
        $COLUMN_TRIPS_MONEY_CURRENCY_CODE TEXT,
        $COLUMN_TRIPS_MONEY_CURRENCY_ID TEXT,
        $COLUMN_TRIPS_MONEY_ISONUM TEXT,
        $COLUMN_TRIPS_MONEY_POS_AVAILABLE TEXT,
        $COLUMN_TRIPS_MONEY_POS_STANDARD TEXT,
        $COLUMN_TRIPS_MONEY_STANDARD TEXT,
        $COLUMN_TRIPS_MONEY_TITLE TEXT,
        FOREIGN KEY($COLUMN_TRIPS_MONEY_BAG_ID) REFERENCES $TABLE_BAGS($COLUMN_BAG_ID)
    )
""".trimIndent()

        val SQL_CREATE_TRIPS_POS_TERMINALS = """
    CREATE TABLE $TABLE_TRIPS_POS_TERMINALS (
        $COLUMN_TRIPS_POS_TERMINAL_ID TEXT PRIMARY KEY,
        $COLUMN_TRIPS_POS_TERMINAL_BAG_ID TEXT,
        $COLUMN_TRIPS_POS_TERMINAL_CODE TEXT,
        $COLUMN_TRIPS_POS_TERMINAL_IP TEXT,
        $COLUMN_TRIPS_POS_TERMINAL_LASER TEXT,
        $COLUMN_TRIPS_POS_TERMINAL_MAC TEXT,
        $COLUMN_TRIPS_POS_TERMINAL_POS_TERMINAL_ID TEXT,
        $COLUMN_TRIPS_POS_TERMINAL_STATUS TEXT,
        $COLUMN_TRIPS_POS_TERMINAL_TRIP_ID TEXT,
        FOREIGN KEY($COLUMN_TRIPS_POS_TERMINAL_BAG_ID) REFERENCES $TABLE_BAGS($COLUMN_BAG_ID),
        FOREIGN KEY($COLUMN_TRIPS_POS_TERMINAL_TRIP_ID) REFERENCES $TABLE_TRIPS($COLUMN_TRIP_ID)
    )
""".trimIndent()

        val SQL_CREATE_TRIPS_PRODUCTS = """
    CREATE TABLE $TABLE_TRIPS_PRODUCTS (
        $COLUMN_TRIPS_PRODUCT_ID TEXT PRIMARY KEY,
        $COLUMN_TRIPS_PRODUCT_BARCODE TEXT,
        $COLUMN_TRIPS_PRODUCT_CART_NUMBER TEXT,
        $COLUMN_TRIPS_PRODUCT_COST TEXT,
        $COLUMN_TRIPS_PRODUCT_IS_CONTROLLED TEXT,
        $COLUMN_TRIPS_PRODUCT_IS_ENABLED TEXT,
        $COLUMN_TRIPS_PRODUCT_PHYSICAL TEXT,
        $COLUMN_TRIPS_PRODUCT_PRODUCT_ID TEXT,
        $COLUMN_TRIPS_PRODUCT_PRODUCT_TYPE TEXT,
        $COLUMN_TRIPS_PRODUCT_QUANTITY TEXT,
        $COLUMN_TRIPS_PRODUCT_TITLE TEXT,
        $COLUMN_TRIPS_PRODUCT_TRAY_ID TEXT,
        $COLUMN_TRIPS_PRODUCT_TRAY_TITLE TEXT,
        $COLUMN_TRIPS_PRODUCT_TRIP_ID TEXT,
        $COLUMN_TRIPS_PRODUCT_VOLUME TEXT,
        $COLUMN_TRIPS_PRODUCT_WEIGHT TEXT,
        FOREIGN KEY($COLUMN_TRIPS_PRODUCT_TRIP_ID) REFERENCES $TABLE_TRIPS($COLUMN_TRIP_ID)
    )
""".trimIndent()


        db.execSQL(CREATE_BARCODES_TABLE)
        db.execSQL(CREATE_ATTENDANTS_TABLE)
        db.execSQL(CREATE_CURRENCY_TABLE)
        db.execSQL(CREATE_MERCHANT_TABLE)
        db.execSQL(SQL_CREATE_DISCOUNTS)
        db.execSQL(SQL_CREATE_DISCOUNTS_CATEGORIES)
        db.execSQL(SQL_CREATE_DISCOUNTS_GOODS)
        db.execSQL(SQL_CREATE_PLANES)
        db.execSQL(SQL_CREATE_SEATS)
        db.execSQL(SQL_CREATE_ROWS)
        db.execSQL(SQL_CREATE_TRIPS)
        db.execSQL(SQL_CREATE_BAGS)
        db.execSQL(SQL_CREATE_GOODS)
        db.execSQL(SQL_CREATE_CURRENT_PRICES)
        db.execSQL(SQL_CREATE_LEGS)
        db.execSQL(SQL_CREATE_FISCAL_MACHINES)
        db.execSQL(SQL_CREATE_FLIGHTS)
        db.execSQL(SQL_CREATE_TAXES)
        db.execSQL(SQL_CREATE_MODIFIERS)
        db.execSQL(SQL_CREATE_TRIPS_MONEY)
        db.execSQL(SQL_CREATE_TRIPS_POS_TERMINALS)
        db.execSQL(SQL_CREATE_TRIPS_PRODUCTS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        //need to write migration queries if needed
        onCreate(db)
    }

    override suspend fun insertBarcodes(list: List<Barcodes>) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            for (barcode in list) {
                val values = ContentValues().apply {
                    put(COLUMN_BARCODE_ID, barcode.id)
                    put(COLUMN_BARCODE_CODE, barcode.code)
                    put(COLUMN_BARCODE_PRODUCT_ID, barcode.productId)
                }
                db.insertWithOnConflict(
                    TABLE_BARCODES,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE
                )
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    override suspend fun insertCurrency(list: List<Currency>) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            for (currency in list) {
                val values = ContentValues().apply {
                    put(COLUMN_CURRENCY_ID, currency.id)
                    put(COLUMN_CURRENCY_TITLE, currency.title)
                    put(COLUMN_CURRENCY_CODE, currency.code)
                    put(COLUMN_CURRENCY_STANDARD, currency.standard)
                    put(COLUMN_CURRENCY_POS_AVAILABLE, currency.posAvailable)
                    put(COLUMN_CURRENCY_POS_STANDARD, currency.posStandard)
                    put(COLUMN_CURRENCY_ISONUM, currency.isonum)
                }
                db.insertWithOnConflict(
                    TABLE_CURRENCY,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE
                )
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    override suspend fun insertMerchant(list: List<Merchant>) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            for (merchant in list) {
                val values = ContentValues().apply {
                    put(COLUMN_MERCHANT_ID, merchant.id)
                    put(COLUMN_MERCHANT_LOGO, merchant.logo)
                    put(COLUMN_MERCHANT_ADDRESS, merchant.address)
                    put(COLUMN_MERCHANT_BIN, merchant.bin)
                    put(COLUMN_MERCHANT_IDENTITY_NUMBER, merchant.identityNumber)
                    put(COLUMN_MERCHANT_REGISTRATION_NUMBER, merchant.registrationNumber)
                    put(COLUMN_MERCHANT_FOOTER, merchant.footer)
                    put(COLUMN_MERCHANT_TAX_ID, merchant.taxId)
                    put(COLUMN_MERCHANT_CASH_AVAILABLE, merchant.cashAvailable)
                    put(COLUMN_MERCHANT_CARD_AVAILABLE, merchant.cardAvailable)
                    put(COLUMN_MERCHANT_MID, merchant.mid)
                    put(COLUMN_MERCHANT_TID, merchant.tid)
                    put(COLUMN_MERCHANT_ABOVE_QR, merchant.aboveQr)
                    put(COLUMN_MERCHANT_BELOW_QR, merchant.belowQr)
                }
                db.insertWithOnConflict(
                    TABLE_MERCHANT,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE
                )
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    override suspend fun insertAttendants(list: List<AttendantsDto>) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            for (attendant in list) {
                val values = ContentValues().apply {
                    put(COLUMN_ATTENDANT_ID, attendant.id)
                    put(COLUMN_ATTENDANT_CODE, attendant.code)
                    put(COLUMN_ATTENDANT_NAME, attendant.name)
                    put(COLUMN_ATTENDANT_BASE_ID, attendant.baseId)
                    put(COLUMN_ATTENDANT_TYPE, attendant.type)
                    put(COLUMN_ATTENDANT_EMAIL, attendant.email)
                    put(COLUMN_ATTENDANT_LANGUAGE, attendant.language)
                }
                db.insertWithOnConflict(
                    TABLE_ATTENDANTS,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE
                )
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    override suspend fun insertDiscountWithCategoriesAndGoods(discountList: List<Discount>) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            for (discount in discountList) {

                // Insert into discounts table
                val discountValues = ContentValues().apply {
                    put(COLUMN_DISCOUNT_ID, discount.id)
                    put(COLUMN_TYPE, discount.type)
                    put(COLUMN_CODE, discount.code)
                    put(COLUMN_VALUE, discount.value)
                    put(COLUMN_MINIMUM_AMOUNT, discount.minimum_amount)
                    put(COLUMN_MINIMUM_QUANTITY, discount.minimum_quantity)
                    put(COLUMN_LIMIT_TOTAL, discount.limit_total)
                    put(COLUMN_LIMIT_ONE_CUSTOMER, discount.limit_one_customer)
                    put(COLUMN_START_DATE, discount.start_date)
                    put(COLUMN_EXPIRE_DATE, discount.expire_date)
                    put(COLUMN_IS_ENABLED, discount.is_enabled)
                }
                db.insertWithOnConflict(
                    TABLE_DISCOUNTS, null, discountValues,
                    SQLiteDatabase.CONFLICT_REPLACE
                )

                // Insert into discounts_categories table
                for (category in discount.discounts_categories ?: mutableListOf()) {
                    val categoryValues = ContentValues().apply {
                        put(COLUMN_DISCOUNT_ID, category.discount_id)
                        put(COLUMN_CATEGORY_ID, category.category_id)
                    }
                    db.insertWithOnConflict(
                        TABLE_DISCOUNTS_CATEGORIES,
                        null,
                        categoryValues,
                        SQLiteDatabase.CONFLICT_REPLACE
                    )
                }

                // Insert into discounts_goods table
                for (good in discount.discounts_goods ?: mutableListOf()) {
                    val goodValues = ContentValues().apply {
                        put(COLUMN_DISCOUNT_ID, good.discount_id)
                        put(COLUMN_GOOD_ID, good.good_id)
                    }
                    db.insertWithOnConflict(
                        TABLE_DISCOUNTS_GOODS,
                        null,
                        goodValues,
                        SQLiteDatabase.CONFLICT_REPLACE
                    )
                }
            }
            db.setTransactionSuccessful();
        } catch (e: Exception) {
            // Handle exception (log it, etc.)
            e.printStackTrace()
        } finally {
            // End transaction (commit if successful, rollback if not)
            db.endTransaction()
        }
    }

    override suspend fun insertPlanes(list: List<Plane>) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            for (plane in list) {
                val values = ContentValues().apply {
                    put(COLUMN_PLANE_ID, plane.id)
                    put(COLUMN_PLANE_CODE, plane.code)
                }
                db.insertWithOnConflict(
                    TABLE_PLANES, null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE
                )
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    override suspend fun insertSeat(seat: Seat) {
        var seatId: Long = -1L
        val db = writableDatabase
        // Insert into seats table
        val seatValues = ContentValues().apply {
            put(COLUMN_SEAT_TYPE, seat.type)
            put(COLUMN_SEAT_WIDTH, seat.width)
            put(COLUMN_SEAT_LENGTH, seat.length)
            put(COLUMN_SEAT_ROW_NUMBER, seat.row_number)
            put(COLUMN_SEAT_WING, seat.wing)
        }
        seatId =
            db.insertWithOnConflict(TABLE_SEATS, null, seatValues, SQLiteDatabase.CONFLICT_REPLACE)

        // Insert into rows table and parameters table
        for (row in seat.row) {
            val rowValues = ContentValues().apply {
                put(COLUMN_ROW_TYPE, row.type)
                put(COLUMN_ROW_WIDTH, row.width)
                put(COLUMN_ROW_SEAT_ID, seatId)
                put(COLUMN_ROW_PARAMETERS, row.parameters)
            }

            db.insertWithOnConflict(
                TABLE_ROWS,
                null,
                rowValues,
                SQLiteDatabase.CONFLICT_REPLACE
            )
        }
    }

    override suspend fun insertTrip(trip: Trip) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            // Insert Trip data
            val tripValues = ContentValues().apply {
                put(COLUMN_TRIP_ID, trip.id)
                put(COLUMN_TRIP_AIRPLANE_ID, trip.airplane_id)
                put(COLUMN_TRIP_CASH_ALLOWED, trip.cash_allowed)
                put(COLUMN_TRIP_SERVER_TIME, trip.server_time)
                put(COLUMN_TRIP_STATUS, trip.status)
                put(COLUMN_TRIP_TROLLEYS_COUNT, trip.trolleys_count)
                // Add other columns as needed
            }
            db.insertWithOnConflict(TABLE_TRIPS, null, tripValues, SQLiteDatabase.CONFLICT_REPLACE)

            // Insert Bags
            for (bag in trip.bags) {
                val bagValues = ContentValues().apply {
                    put(COLUMN_BAG_ID, bag.id)
                    put(COLUMN_BAG_CAPACITY, bag.capacity)
                    put(COLUMN_BAG_CODE, bag.code)
                    put(COLUMN_BAG_STATUS, bag.status)
                    put(COLUMN_BAG_TRIP_ID, trip.id) // Use trip.id to link to the trip
                    // Add other columns as needed
                }
                db.insertWithOnConflict(
                    TABLE_BAGS,
                    null,
                    bagValues,
                    SQLiteDatabase.CONFLICT_REPLACE
                )
            }

            // Insert Goods
            for (good in trip.goods) {
                val goodValues = ContentValues().apply {
                    put(COLUMN_GOODS_ID, good.id)
                    put(COLUMN_GOOD_CATEGORY, good.category)
                    put(COLUMN_GOOD_CATEGORY_ID, good.category_id)
                    put(COLUMN_GOOD_IMAGE_CODE, good.image_code)
                    put(COLUMN_GOOD_IMAGE_ID, good.image_id)
                    put(COLUMN_GOOD_IS_HIDDEN, good.is_hidden)
                    put(COLUMN_GOOD_MERCHANT_ID, good.merchant_id)
                    put(COLUMN_GOOD_PRODUCT_ID, good.product_id)
                    put(COLUMN_GOOD_TAG_ID, good.tag_id)
                    put(COLUMN_GOOD_TITLE, good.title)
                    // Add other columns as needed
                }
                db.insertWithOnConflict(
                    TABLE_GOODS,
                    null,
                    goodValues,
                    SQLiteDatabase.CONFLICT_REPLACE
                )

                // Insert Current Prices
                for (currentPrice in good.current_prices) {
                    val priceValues = ContentValues().apply {
                        put(COLUMN_CURRENT_PRICE_ID, currentPrice.id)
                        put(
                            COLUMN_CURRENT_PRICE_GOOD_ID,
                            good.id
                        ) // Use good.id to link to the good
                        put(COLUMN_CURRENT_PRICE, currentPrice.price)
                        put(COLUMN_CURRENT_PRICE_CURRENCY_ID, currentPrice.currency_id)
                        put(COLUMN_CURRENT_PRICE_PTYPE, currentPrice.ptype)
                        put(COLUMN_CURRENT_PRICE_TYPE, currentPrice.type)
                        // Add other columns as needed
                    }
                    db.insertWithOnConflict(
                        TABLE_CURRENT_PRICES,
                        null,
                        priceValues,
                        SQLiteDatabase.CONFLICT_REPLACE
                    )
                }
            }

            // Insert Legs
            for (leg in trip.legs) {
                val legValues = ContentValues().apply {
                    put(COLUMN_LEG_ID, leg.id)
                    put(COLUMN_LEG_AIRPLANE_ID, leg.airplane_id)
                    put(COLUMN_LEG_ARRIVAL, leg.arrival)
                    put(COLUMN_LEG_DEPARTURE, leg.departure)
                    put(COLUMN_LEG_FISCAL_MACHINE_ID, leg.fiscal_machine_id)
                    put(COLUMN_LEG_FLIGHT_ID, leg.flight_id)
                    put(COLUMN_LEG_REL_TITLE, leg.rel_title)
                    put(COLUMN_LEG_TRIP_ID, trip.id) // Use trip.id to link to the trip
                    // Add other columns as needed
                }
                db.insertWithOnConflict(
                    TABLE_LEGS,
                    null,
                    legValues,
                    SQLiteDatabase.CONFLICT_REPLACE
                )

                // Insert Fiscal Machine
                val fiscalMachineValues = ContentValues().apply {
                    put(COLUMN_FISCAL_MACHINE_ID, leg.fiscal_machine.id)
                    put(COLUMN_FISCAL_MACHINE_ABOVE_QR, leg.fiscal_machine.above_qr)
                    put(COLUMN_FISCAL_MACHINE_BELOW_QR, leg.fiscal_machine.below_qr)
                    put(COLUMN_FISCAL_MACHINE_ADDRESS, leg.fiscal_machine.address)
                    put(COLUMN_FISCAL_MACHINE_BIN, leg.fiscal_machine.bin)
                    put(COLUMN_FISCAL_MACHINE_IDENTITY_NUMBER, leg.fiscal_machine.identity_number)
                    put(
                        COLUMN_FISCAL_MACHINE_REGISTRATION_NUMBER,
                        leg.fiscal_machine.registration_number
                    )
                    put(COLUMN_FISCAL_MACHINE_TITLE, leg.fiscal_machine.title)
                    // Add other columns as needed
                }
                db.insertWithOnConflict(
                    TABLE_FISCAL_MACHINES,
                    null,
                    fiscalMachineValues,
                    SQLiteDatabase.CONFLICT_REPLACE
                )

                // Insert Flight
                val flightValues = ContentValues().apply {
                    put(COLUMN_FLIGHT_ID, leg.flight.id)
                    put(COLUMN_FLIGHT_ARRIVAL, leg.flight.arrival)
                    put(COLUMN_FLIGHT_CATEGORY, leg.flight.category)
                    put(COLUMN_FLIGHT_CODE, leg.flight.code)
                    put(COLUMN_FLIGHT_DEPARTURE, leg.flight.departure)
                    put(COLUMN_FLIGHT_REL_TITLE, leg.flight.rel_title)
                    // Add other columns as needed
                }
                db.insertWithOnConflict(
                    TABLE_FLIGHTS,
                    null,
                    flightValues,
                    SQLiteDatabase.CONFLICT_REPLACE
                )

                // Insert Taxes
                for (tax in leg.flight.taxes) {
                    val taxValues = ContentValues().apply {
                        put(COLUMN_TAX_ID, tax.id)
                        put(
                            COLUMN_TAX_FLIGHT_ID,
                            leg.flight.id
                        ) // Use leg.flight.id to link to the flight
                        put(COLUMN_TAX_PERCENT, tax.percent)
                        put(COLUMN_TAX_TITLE, tax.title)
                        // Add other columns as needed
                    }
                    db.insertWithOnConflict(
                        TABLE_TAXES,
                        null,
                        taxValues,
                        SQLiteDatabase.CONFLICT_REPLACE
                    )
                }

                // Insert Modifiers
                for (modifier in leg.modifiers) {
                    val modifierValues = ContentValues().apply {
                        put(COLUMN_MODIFIER_ID, modifier.id)
                        put(COLUMN_MODIFIER_FLIGHT_ID, modifier.flight_id)
                        put(COLUMN_MODIFIER_GOOD_ID, modifier.good_id)
                        put(COLUMN_MODIFICATOR, modifier.modificator)
                        // Add other columns as needed
                    }
                    db.insertWithOnConflict(
                        TABLE_MODIFIERS,
                        null,
                        modifierValues,
                        SQLiteDatabase.CONFLICT_REPLACE
                    )
                }
            }

            // Insert TripsMoney
            for (money in trip.trips_money) {
                val moneyValues = ContentValues().apply {
                    put(COLUMN_TRIPS_MONEY_ID, money.id)
                    put(COLUMN_TRIPS_MONEY_AMOUNT, money.amount)
                    put(COLUMN_TRIPS_MONEY_BAG_ID, money.bag_id)
                    put(COLUMN_TRIPS_MONEY_CURRENCY_CODE, money.currency_code)
                    put(COLUMN_TRIPS_MONEY_CURRENCY_ID, money.currency_id)
                    put(COLUMN_TRIPS_MONEY_ISONUM, money.isonum)
                    put(COLUMN_TRIPS_MONEY_POS_AVAILABLE, money.pos_available)
                    put(COLUMN_TRIPS_MONEY_POS_STANDARD, money.pos_standard)
                    put(COLUMN_TRIPS_MONEY_STANDARD, money.standard)
                    put(COLUMN_TRIPS_MONEY_TITLE, money.title)
                    // Add other columns as needed
                }
                db.insertWithOnConflict(
                    TABLE_TRIPS_MONEY,
                    null,
                    moneyValues,
                    SQLiteDatabase.CONFLICT_REPLACE
                )
            }

            // Insert TripsPosTerminals
            for (posTerminal in trip.trips_pos_terminals) {
                val posTerminalValues = ContentValues().apply {
                    put(COLUMN_TRIPS_POS_TERMINAL_ID, posTerminal.id)
                    put(COLUMN_TRIPS_POS_TERMINAL_BAG_ID, posTerminal.bag_id)
                    put(COLUMN_TRIPS_POS_TERMINAL_CODE, posTerminal.code)
                    put(COLUMN_TRIPS_POS_TERMINAL_IP, posTerminal.ip)
                    put(COLUMN_TRIPS_POS_TERMINAL_LASER, posTerminal.laser)
                    put(COLUMN_TRIPS_POS_TERMINAL_MAC, posTerminal.mac)
                    put(COLUMN_TRIPS_POS_TERMINAL_POS_TERMINAL_ID, posTerminal.pos_terminal_id)
                    put(COLUMN_TRIPS_POS_TERMINAL_STATUS, posTerminal.status)
                    put(
                        COLUMN_TRIPS_POS_TERMINAL_TRIP_ID,
                        trip.id
                    ) // Use trip.id to link to the trip
                    // Add other columns as needed
                }
                db.insertWithOnConflict(
                    TABLE_TRIPS_POS_TERMINALS,
                    null,
                    posTerminalValues,
                    SQLiteDatabase.CONFLICT_REPLACE
                )
            }

            // Insert TripsProducts
            for (product in trip.trips_products) {
                val productValues = ContentValues().apply {
                    put(COLUMN_TRIPS_PRODUCT_BARCODE, product.barcode)
                    put(COLUMN_TRIPS_PRODUCT_CART_NUMBER, product.cart_number)
                    put(COLUMN_TRIPS_PRODUCT_COST, product.cost)
                    put(COLUMN_TRIPS_PRODUCT_IS_CONTROLLED, product.is_controlled)
                    put(COLUMN_TRIPS_PRODUCT_IS_ENABLED, product.is_enabled)
                    put(COLUMN_TRIPS_PRODUCT_PHYSICAL, product.physical)
                    put(COLUMN_TRIPS_PRODUCT_PRODUCT_ID, product.product_id)
                    put(COLUMN_TRIPS_PRODUCT_PRODUCT_TYPE, product.product_type)
                    put(COLUMN_TRIPS_PRODUCT_QUANTITY, product.quantity)
                    put(COLUMN_TRIPS_PRODUCT_TITLE, product.title)
                    put(COLUMN_TRIPS_PRODUCT_TRAY_ID, product.tray_id)
                    put(COLUMN_TRIPS_PRODUCT_TRAY_TITLE, product.tray_title)
                    put(COLUMN_TRIPS_PRODUCT_TRIP_ID, trip.id) // Use trip.id to link to the trip
                    put(COLUMN_TRIPS_PRODUCT_VOLUME, product.volume)
                    put(COLUMN_TRIPS_PRODUCT_WEIGHT, product.weight)
                    // Add other columns as needed
                }
                db.insertWithOnConflict(
                    TABLE_TRIPS_PRODUCTS,
                    null,
                    productValues,
                    SQLiteDatabase.CONFLICT_REPLACE
                )
            }

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    override suspend fun retrievingAttendants(): List<AttendantsDto> {
        val db = readableDatabase
        val projection = arrayOf(
            COLUMN_ATTENDANT_ID,
            COLUMN_ATTENDANT_CODE,
            COLUMN_ATTENDANT_NAME,
            COLUMN_ATTENDANT_BASE_ID,
            COLUMN_ATTENDANT_TYPE,
            COLUMN_ATTENDANT_EMAIL,
            COLUMN_ATTENDANT_LANGUAGE
        )

        val sortOrder = "$COLUMN_ATTENDANT_NAME ASC"

        val cursor = db.query(
            TABLE_ATTENDANTS,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )

        val attendantsList = mutableListOf<AttendantsDto>()
        cursor.use {
            while (it.moveToNext()) {
                val attendant = AttendantsDto(
                    it.getString(it.getColumnIndexOrThrow(COLUMN_ATTENDANT_ID)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_ATTENDANT_CODE)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_ATTENDANT_NAME)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_ATTENDANT_BASE_ID)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_ATTENDANT_TYPE)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_ATTENDANT_EMAIL)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_ATTENDANT_LANGUAGE))
                )
                attendantsList.add(attendant)
            }
        }

        return attendantsList
    }

    companion object {
        private const val DB_NAME = "skyInfoDB"
        private const val DB_VERSION = 1


        // Barcodes
        const val TABLE_BARCODES = "barcodes"
        const val COLUMN_BARCODE_ID = "id"
        const val COLUMN_BARCODE_CODE = "code"
        const val COLUMN_BARCODE_PRODUCT_ID = "product_id"

        // Attendants
        const val TABLE_ATTENDANTS = "attendants"
        const val COLUMN_ATTENDANT_ID = "id"
        const val COLUMN_ATTENDANT_CODE = "code"
        const val COLUMN_ATTENDANT_NAME = "name"
        const val COLUMN_ATTENDANT_BASE_ID = "base_id"
        const val COLUMN_ATTENDANT_TYPE = "type"
        const val COLUMN_ATTENDANT_EMAIL = "email"
        const val COLUMN_ATTENDANT_LANGUAGE = "language"

        // Currency
        const val TABLE_CURRENCY = "currency"
        const val COLUMN_CURRENCY_ID = "id"
        const val COLUMN_CURRENCY_TITLE = "title"
        const val COLUMN_CURRENCY_CODE = "code"
        const val COLUMN_CURRENCY_STANDARD = "standard"
        const val COLUMN_CURRENCY_POS_AVAILABLE = "pos_available"
        const val COLUMN_CURRENCY_POS_STANDARD = "pos_standard"
        const val COLUMN_CURRENCY_ISONUM = "isonum"

        // Merchant
        const val TABLE_MERCHANT = "merchant"
        const val COLUMN_MERCHANT_ID = "id"
        const val COLUMN_MERCHANT_LOGO = "logo"
        const val COLUMN_MERCHANT_ADDRESS = "address"
        const val COLUMN_MERCHANT_BIN = "bin"
        const val COLUMN_MERCHANT_IDENTITY_NUMBER = "identityNumber"
        const val COLUMN_MERCHANT_REGISTRATION_NUMBER = "registrationNumber"
        const val COLUMN_MERCHANT_FOOTER = "footer"
        const val COLUMN_MERCHANT_TAX_ID = "taxId"
        const val COLUMN_MERCHANT_CASH_AVAILABLE = "cashAvailable"
        const val COLUMN_MERCHANT_CARD_AVAILABLE = "cardAvailable"
        const val COLUMN_MERCHANT_MID = "mid"
        const val COLUMN_MERCHANT_TID = "tid"
        const val COLUMN_MERCHANT_ABOVE_QR = "aboveQr"
        const val COLUMN_MERCHANT_BELOW_QR = "belowQr"

        //Discounts
        private const val TABLE_DISCOUNTS = "discounts"
        private const val COLUMN_DISCOUNT_ID = "id"
        private const val COLUMN_TYPE = "type"
        private const val COLUMN_CODE = "code"
        private const val COLUMN_VALUE = "value"
        private const val COLUMN_MINIMUM_AMOUNT = "minimum_amount"
        private const val COLUMN_MINIMUM_QUANTITY = "minimum_quantity"
        private const val COLUMN_LIMIT_TOTAL = "limit_total"
        private const val COLUMN_LIMIT_ONE_CUSTOMER = "limit_one_customer"
        private const val COLUMN_START_DATE = "start_date"
        private const val COLUMN_EXPIRE_DATE = "expire_date"
        private const val COLUMN_IS_ENABLED = "is_enabled"

        // Discounts categories table
        private const val TABLE_DISCOUNTS_CATEGORIES = "discounts_categories"
        private const val COLUMN_CATEGORY_ID = "category_id"

        // Discounts goods table
        private const val TABLE_DISCOUNTS_GOODS = "discounts_goods"
        private const val COLUMN_GOOD_ID = "good_id"


        // Plane table
        private const val TABLE_PLANES = "planes"
        private const val COLUMN_PLANE_ID = "id"
        private const val COLUMN_PLANE_CODE = "code"

        //Seat table
        private const val TABLE_SEATS = "seats"
        private const val COLUMN_SEAT_ID = "id"
        private const val COLUMN_SEAT_TYPE = "type"
        private const val COLUMN_SEAT_WIDTH = "width"
        private const val COLUMN_SEAT_LENGTH = "length"
        private const val COLUMN_SEAT_ROW_NUMBER = "row_number"
        private const val COLUMN_SEAT_WING = "wing"

        // Row table
        private const val TABLE_ROWS = "rows"
        private const val COLUMN_ROW_ID = "id"
        private const val COLUMN_ROW_TYPE = "type"
        private const val COLUMN_ROW_WIDTH = "width"
        private const val COLUMN_ROW_SEAT_ID = "seat_id"
        private const val COLUMN_ROW_PARAMETERS = "parameters"


        // Trip table
        private const val TABLE_TRIPS = "trips"
        private const val COLUMN_TRIP_ID = "id"
        private const val COLUMN_TRIP_AIRPLANE_ID = "airplane_id"
        private const val COLUMN_TRIP_CASH_ALLOWED = "cash_allowed"
        private const val COLUMN_TRIP_SERVER_TIME = "server_time"
        private const val COLUMN_TRIP_STATUS = "status"
        private const val COLUMN_TRIP_TROLLEYS_COUNT = "trolleys_count"

        // Bag table
        private const val TABLE_BAGS = "bags"
        private const val COLUMN_BAG_ID = "id"
        private const val COLUMN_BAG_CAPACITY = "capacity"
        private const val COLUMN_BAG_CODE = "code"
        private const val COLUMN_BAG_STATUS = "status"
        private const val COLUMN_BAG_TRIP_ID = "trip_id"

        // Good table
        private const val TABLE_GOODS = "goods"
        private const val COLUMN_GOODS_ID = "id"
        private const val COLUMN_GOOD_CATEGORY = "category"
        private const val COLUMN_GOOD_CATEGORY_ID = "category_id"
        private const val COLUMN_GOOD_IMAGE_CODE = "image_code"
        private const val COLUMN_GOOD_IMAGE_ID = "image_id"
        private const val COLUMN_GOOD_IS_HIDDEN = "is_hidden"
        private const val COLUMN_GOOD_MERCHANT_ID = "merchant_id"
        private const val COLUMN_GOOD_PRODUCT_ID = "product_id"
        private const val COLUMN_GOOD_TAG_ID = "tag_id"
        private const val COLUMN_GOOD_TITLE = "title"

        // CurrentPrice table
        private const val TABLE_CURRENT_PRICES = "current_prices"
        private const val COLUMN_CURRENT_PRICE_ID = "id"
        private const val COLUMN_CURRENT_PRICE_CURRENCY_ID = "currency_id"
        private const val COLUMN_CURRENT_PRICE_GOOD_ID = "good_id"
        private const val COLUMN_CURRENT_PRICE = "price"
        private const val COLUMN_CURRENT_PRICE_PTYPE = "ptype"
        private const val COLUMN_CURRENT_PRICE_TYPE = "type"

        // Leg table
        private const val TABLE_LEGS = "legs"
        private const val COLUMN_LEG_ID = "id"
        private const val COLUMN_LEG_AIRPLANE_ID = "airplane_id"
        private const val COLUMN_LEG_ARRIVAL = "arrival"
        private const val COLUMN_LEG_DEPARTURE = "departure"
        private const val COLUMN_LEG_FISCAL_MACHINE_ID = "fiscal_machine_id"
        private const val COLUMN_LEG_FLIGHT_ID = "flight_id"
        private const val COLUMN_LEG_REL_TITLE = "rel_title"
        private const val COLUMN_LEG_TRIP_ID = "trip_id"

        // FiscalMachine table
        private const val TABLE_FISCAL_MACHINES = "fiscal_machines"
        private const val COLUMN_FISCAL_MACHINE_ID = "id"
        private const val COLUMN_FISCAL_MACHINE_ABOVE_QR = "above_qr"
        private const val COLUMN_FISCAL_MACHINE_ADDRESS = "address"
        private const val COLUMN_FISCAL_MACHINE_BELOW_QR = "below_qr"
        private const val COLUMN_FISCAL_MACHINE_BIN = "bin"
        private const val COLUMN_FISCAL_MACHINE_IDENTITY_NUMBER = "identity_number"
        private const val COLUMN_FISCAL_MACHINE_REGISTRATION_NUMBER = "registration_number"
        private const val COLUMN_FISCAL_MACHINE_TITLE = "title"

        // Flight table
        private const val TABLE_FLIGHTS = "flights"
        private const val COLUMN_FLIGHT_ID = "id"
        private const val COLUMN_FLIGHT_ARRIVAL = "arrival"
        private const val COLUMN_FLIGHT_CATEGORY = "category"
        private const val COLUMN_FLIGHT_CODE = "code"
        private const val COLUMN_FLIGHT_DEPARTURE = "departure"
        private const val COLUMN_FLIGHT_REL_TITLE = "rel_title"

        // Taxe table
        private const val TABLE_TAXES = "taxes"
        private const val COLUMN_TAX_ID = "id"
        private const val COLUMN_TAX_FLIGHT_ID = "flight_id"
        private const val COLUMN_TAX_PERCENT = "percent"
        private const val COLUMN_TAX_TITLE = "title"

        // Modifier table
        private const val TABLE_MODIFIERS = "modifiers"
        private const val COLUMN_MODIFIER_ID = "id"
        private const val COLUMN_MODIFIER_FLIGHT_ID = "flight_id"
        private const val COLUMN_MODIFIER_GOOD_ID = "good_id"
        private const val COLUMN_MODIFICATOR = "modificator"

        // TripsMoney table
        private const val TABLE_TRIPS_MONEY = "trips_money"
        private const val COLUMN_TRIPS_MONEY_ID = "id"
        private const val COLUMN_TRIPS_MONEY_AMOUNT = "amount"
        private const val COLUMN_TRIPS_MONEY_BAG_ID = "bag_id"
        private const val COLUMN_TRIPS_MONEY_CURRENCY_CODE = "currency_code"
        private const val COLUMN_TRIPS_MONEY_CURRENCY_ID = "currency_id"
        private const val COLUMN_TRIPS_MONEY_ISONUM = "isonum"
        private const val COLUMN_TRIPS_MONEY_POS_AVAILABLE = "pos_available"
        private const val COLUMN_TRIPS_MONEY_POS_STANDARD = "pos_standard"
        private const val COLUMN_TRIPS_MONEY_STANDARD = "standard"
        private const val COLUMN_TRIPS_MONEY_TITLE = "title"

        // TripsPosTerminal table
        private const val TABLE_TRIPS_POS_TERMINALS = "trips_pos_terminals"
        private const val COLUMN_TRIPS_POS_TERMINAL_ID = "id"
        private const val COLUMN_TRIPS_POS_TERMINAL_BAG_ID = "bag_id"
        private const val COLUMN_TRIPS_POS_TERMINAL_CODE = "code"
        private const val COLUMN_TRIPS_POS_TERMINAL_IP = "ip"
        private const val COLUMN_TRIPS_POS_TERMINAL_LASER = "laser"
        private const val COLUMN_TRIPS_POS_TERMINAL_MAC = "mac"
        private const val COLUMN_TRIPS_POS_TERMINAL_POS_TERMINAL_ID = "pos_terminal_id"
        private const val COLUMN_TRIPS_POS_TERMINAL_STATUS = "status"
        private const val COLUMN_TRIPS_POS_TERMINAL_TRIP_ID = "trip_id"

        // TripsProduct table
        private const val TABLE_TRIPS_PRODUCTS = "trips_products"
        private const val COLUMN_TRIPS_PRODUCT_ID = "id"
        private const val COLUMN_TRIPS_PRODUCT_BARCODE = "barcode"
        private const val COLUMN_TRIPS_PRODUCT_CART_NUMBER = "cart_number"
        private const val COLUMN_TRIPS_PRODUCT_COST = "cost"
        private const val COLUMN_TRIPS_PRODUCT_IS_CONTROLLED = "is_controlled"
        private const val COLUMN_TRIPS_PRODUCT_IS_ENABLED = "is_enabled"
        private const val COLUMN_TRIPS_PRODUCT_PHYSICAL = "physical"
        private const val COLUMN_TRIPS_PRODUCT_PRODUCT_ID = "product_id"
        private const val COLUMN_TRIPS_PRODUCT_PRODUCT_TYPE = "product_type"
        private const val COLUMN_TRIPS_PRODUCT_QUANTITY = "quantity"
        private const val COLUMN_TRIPS_PRODUCT_TITLE = "title"
        private const val COLUMN_TRIPS_PRODUCT_TRAY_ID = "tray_id"
        private const val COLUMN_TRIPS_PRODUCT_TRAY_TITLE = "tray_title"
        private const val COLUMN_TRIPS_PRODUCT_TRIP_ID = "trip_id"
        private const val COLUMN_TRIPS_PRODUCT_VOLUME = "volume"
        private const val COLUMN_TRIPS_PRODUCT_WEIGHT = "weight"

    }
}