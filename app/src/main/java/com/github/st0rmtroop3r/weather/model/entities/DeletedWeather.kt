package com.github.st0rmtroop3r.weather.model.entities

import androidx.room.*

/**
 * Database table entity that used for marking specified Weather as deleted
 */

@Entity(
    tableName = "deleted",
    foreignKeys = [
        ForeignKey(entity = Weather::class,
                parentColumns = ["id"],
                childColumns = ["city_id"],
                onDelete = ForeignKey.CASCADE)],
    indices = [Index("city_id")]
    )
data class DeletedWeather(

    /** An id of the city which weather data should be deleted */
    @PrimaryKey
    @ColumnInfo(name = "city_id")
    val cityId: Long
)