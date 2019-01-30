package com.github.st0rmtroop3r.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.st0rmtroop3r.weather.R
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.viewmodel.AddWeatherViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add_weather.*
import kotlinx.android.synthetic.main.view_search_result.*
import javax.inject.Inject

class AddWeatherFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var inputMethodManager: InputMethodManager

    private lateinit var viewModel: AddWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(AddWeatherViewModel::class.java)

        edt_search_city_name?.requestFocus()

        view_tint?.setOnClickListener {
            hideKeyboard()
            activity?.onBackPressed()
        }

        btn_search_city_name?.setOnClickListener {
            viewModel.onSearchClicked(edt_search_city_name?.text.toString())
        }

        btn_add_weather.setOnClickListener { viewModel.onAddClicked() }

        viewModel.keyboardIsOpen.observe(this, Observer {
            if (it) showKeyboard() else hideKeyboard()
        })

        viewModel.result.observe(this, Observer { fillWeatherViews(it) })

        viewModel.resultIsVisible.observe(this, Observer {
            layout_weather_search_result?.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.resultError.observe(this, Observer { txv_search_city_error.text = it })

        viewModel.resultErrorIsVisible.observe(this, Observer {
            txv_search_city_error.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })

        viewModel.showProgressBar.observe(this, Observer {
            if (it) {
                btn_search_city_name.visibility = View.GONE
                pgb_searching_city_name.visibility = View.VISIBLE
            } else {
                btn_search_city_name.visibility = View.VISIBLE
                pgb_searching_city_name.visibility = View.GONE
            }
        })

        viewModel.icon.observe(this, Observer { it?.into(imv_weather_icon) })

    }

    private fun fillWeatherViews(weather: Weather?) {
        txv_city.text = "${weather?.cityName}, ${weather?.system?.countryCode?.capitalize()}"
        txv_tempreature.text = "temp ${weather?.main?.temperature}"
        txv_wind_speed.text = "wind speed ${weather?.wind?.speed}"
        txv_wind_degree.text = "wind degree ${weather?.wind?.degrees}"
        txv_pressure.text = "pressure ${weather?.main?.pressure}"
        txv_humidity.text = "humidity ${weather?.main?.humidity}"
    }

    private fun showKeyboard() = inputMethodManager.showSoftInput(edt_search_city_name, 0)

    private fun hideKeyboard() = inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

    companion object {
        val TAG = AddWeatherFragment::class.java.simpleName
    }
}