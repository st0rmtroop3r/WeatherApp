package com.github.st0rmtroop3r.weather.view.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.st0rmtroop3r.weather.R
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import kotlinx.android.synthetic.main.view_weather_short_data.view.*
import javax.inject.Inject
import kotlin.math.roundToInt

class CurrentWeatherRecyclerAdapter
    @Inject constructor(
        private val weatherRepository: WeatherRepository,
        private val resources: Resources
) : RecyclerView.Adapter<CurrentWeatherRecyclerAdapter.Holder>() {

    private val weatherList = ArrayList<Weather>()

    override fun getItemCount() = weatherList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_weather_card, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val weather = weatherList.get(position)

        val cityName = resources.getString(R.string.city_name_with_country_code,
            weather.cityName, weather.system.countryCode.capitalize())
        holder.itemView.txv_card_city_name.text = cityName

        val temperature = resources.getString(R.string.temperature_pattern_celcius,
            weather.main.temperature.roundToInt())
        holder.itemView.txv_card_temperature.text = temperature

        weather.conditions?.getOrNull(0)?.also {
            holder.itemView.txv_card_weather_description.text = it.description
            weatherRepository.getWeatherIcon(it.icon)?.into(holder.itemView.imv_card_icon)
        }
    }

    fun setWeatherList(newWeatherList: List<Weather>) {
        val callback = CurrentWeatherDiffUtilCallback(weatherList, newWeatherList)
        val diffResult = DiffUtil.calculateDiff(callback)
        weatherList.clear()
        weatherList.addAll(newWeatherList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getItemAt(position: Int) = weatherList.get(position)

    fun removeItem(position: Int) {
        weatherList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(weather: Weather, adapterPosition: Int) {
        weatherList.add(adapterPosition, weather)
        notifyItemInserted(adapterPosition)
    }

    class Holder(item: View) : RecyclerView.ViewHolder(item)
}