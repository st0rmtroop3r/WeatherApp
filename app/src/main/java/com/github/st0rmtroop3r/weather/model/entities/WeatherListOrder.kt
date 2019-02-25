package com.github.st0rmtroop3r.weather.model.entities

import androidx.room.*

/**
 * Database table entity for storing Weather list order
 */

@Entity(
    tableName = "weather_order",
    foreignKeys = [
        ForeignKey(entity = Weather::class,
            parentColumns = ["id"],
            childColumns = ["city_id"],
            onDelete = ForeignKey.CASCADE)],
    indices = [Index("city_id")]
)
data class WeatherListOrder(

    /** Weather city id */
    @PrimaryKey
    @ColumnInfo(name = "city_id")
    val cityId: Long,

    /** Weather order number in the list */
    @ColumnInfo(name = "order_number")
    val orderNumber: Int
)