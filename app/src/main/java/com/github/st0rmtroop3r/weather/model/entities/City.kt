package com.github.st0rmtroop3r.weather.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class City(

    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "city_name")
    val name: String
)