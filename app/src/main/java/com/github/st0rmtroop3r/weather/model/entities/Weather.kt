package com.github.st0rmtroop3r.weather.model.entities

import androidx.room.*
import com.github.st0rmtroop3r.weather.model.db.WeatherConditionTypeConverter
import com.google.gson.annotations.SerializedName

/**
 *  Data class for OpenWeatherMapApi and Room database entity
 */
@Entity(tableName = "weather")
data class Weather (

    /** City ID */
    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    var cityId: Long = -1,

    /** City name */
    @SerializedName("name")
    @ColumnInfo(name = "city_name")
    var cityName: String,

    /** Time of data calculation, unix, UTC */
    @SerializedName("dt")
    @ColumnInfo(name = "dataTime")
    var dataTime: Long = -1,

    /** Visibility, meter */
    @SerializedName("visibility")
    @ColumnInfo(name = "visibility")
    var visibility: Int = -1,

    /** Internal parameter */
    @SerializedName("cod")
    @ColumnInfo(name = "cod")
    var cod: Int = -1,

    /** Internal parameter */
    @SerializedName("base")
    @ColumnInfo(name = "base")
    var base: String? = "",

    /** City geo location */
    @SerializedName("coord")
    @Embedded(prefix = "coord_")
    var coordinates: Coordinates,

    /** Weather conditions */
    @SerializedName("weather")
    @TypeConverters(WeatherConditionTypeConverter::class)
    var conditions: List<WeatherCondition>?,

    /** Weather main params */
    @SerializedName("main")
    @Embedded(prefix = "main_")
    var main: WeatherParams,

    /** Wind speed and direction */
    @SerializedName("wind")
    @Embedded(prefix = "wind_")
    var wind: Wind,

    /** Cloudiness */
    @SerializedName("clouds")
    @Embedded(prefix = "clouds_")
    var clouds: Clouds,

    /** Rain volume */
    @SerializedName("rain")
    @Embedded(prefix = "rain_")
    var rain: Precipitation? = null,

    /** Snow volume */
    @SerializedName("snow")
    @Embedded(prefix = "snow_")
    var snow: Precipitation? = null,

    @SerializedName("sys")
    @Embedded(prefix = "system_")
    var system: WeatherSystem
)