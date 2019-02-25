package com.github.st0rmtroop3r.weather.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.github.st0rmtroop3r.weather.model.entities.Weather

class CurrentWeatherDiffUtilCallback(
    private val oldList: List<Weather>,
    private val newList: List<Weather>
): DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.get(oldItemPosition).cityId == newList.get(newItemPosition).cityId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition))
    }
}