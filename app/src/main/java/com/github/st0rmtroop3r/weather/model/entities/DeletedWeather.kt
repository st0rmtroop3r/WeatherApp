package com.github.st0rmtroop3r.weather.model.entities

import androidx.room.*

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

    @PrimaryKey
    @ColumnInfo(name = "city_id")
    val cityId: Long
)