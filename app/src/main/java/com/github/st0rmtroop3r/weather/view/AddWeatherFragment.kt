package com.github.st0rmtroop3r.weather.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.transition.ChangeBounds
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.github.st0rmtroop3r.weather.R
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.viewmodel.AddWeatherViewModel
import com.google.android.gms.common.api.ResolvableApiException
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add_weather.*
import kotlinx.android.synthetic.main.view_weather_search_result.*
import kotlinx.android.synthetic.main.view_weather_short_data.*
import javax.inject.Inject
import kotlin.math.roundToInt

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

        tiet_city_name.requestFocus()
        tiet_city_name.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) { til_city_name.error = null }
        })
        tiet_city_name.setOnEditorActionListener { _, actionId, _ -> when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    onSearchClicked()
                    true }
                else -> false } }

        frame_search?.setOnClickListener { close() }

        imv_search_city_weather.setOnClickListener { onSearchClicked() }

        btn_add_weather.setOnClickListener { viewModel.onAddClicked() }

        imv_search_by_location.setOnClickListener { viewModel.onLocationClick() }

        viewModel.keyboardIsOpen.observe(this, Observer {
            if (it) showKeyboard() else hideKeyboard()
        })

        viewModel.searchResult.observe(this, Observer { onResultReceived(it) })

        viewModel.searchError.observe(this, Observer { til_city_name.error = it })

        viewModel.addWeatherResult.observe(this, Observer { onAddWeatherResult(it) })

        viewModel.showProgressBar.observe(this, Observer { showProgressBar(it) })

        viewModel.icon.observe(this, Observer { it?.into(imv_card_icon) })

        viewModel.requestLocationPermission.observe(this, Observer { requestLocationPermission(it) })

        viewModel.requestLocationEnable.observe(this, Observer { requestLocationEnable(it) })

        viewModel.isLocationServiceAvailable.observe(this, Observer { isLocationServiceAvailable(it) })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            locationPermissionRequestCode -> {
                val granted = (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                viewModel.onLocationPermissionResult(granted)
            }
        }
    }

    private fun requestLocationPermission(requested: Boolean) {
        if (requested) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionRequestCode)
        }
    }

    private fun requestLocationEnable(request: ResolvableApiException?) {
        if (request is ResolvableApiException) {
            request.startResolutionForResult(activity, locationSettingsRequestCode)
        } else {
            Log.wtf(TAG, "requestLocationEnable: request is NOT ResolvableApiException")
        }
    }

    fun onLocationSettingsResult(resultCode: Int) {
        viewModel.onLocationSettingsResult(resultCode == -1)
    }

    private fun close() {
        hideKeyboard()
        activity?.onBackPressed()
    }

    private fun onAddWeatherResult(error: String?) {
        error ?. let {
            til_city_name.error = it
        } ?: close()
    }

    private fun onSearchClicked() {
        viewModel.onSearchClicked(tiet_city_name.text.toString())
    }

    private fun showProgressBar(show: Boolean) {
        if (show) {
            imv_search_city_weather.visibility = View.GONE
            pgb_search.visibility = View.VISIBLE
        } else {
            imv_search_city_weather.visibility = View.VISIBLE
            pgb_search.visibility = View.GONE
        }
    }

    private fun onResultReceived(weather: Weather?) {
        if (weather == null) hideResultView() else showResult(weather)
    }

    private fun showResult(weather: Weather) {
        fillWeatherViews(weather)
        beginDelayedTransition()
        layout_weather_search_result.visibility = View.VISIBLE
    }

    private fun hideResultView() {
        beginDelayedTransition()
        layout_weather_search_result.visibility = View.GONE
    }

    private fun beginDelayedTransition() {
        val slide = Slide()
        slide.slideEdge = Gravity.TOP
        val bounds = ChangeBounds()
        val transitionSet = TransitionSet().addTransition(slide).addTransition(bounds)
        transitionSet.excludeTarget(imv_search_city_weather, true)
        transitionSet.excludeTarget(pgb_search, true)
        TransitionManager.beginDelayedTransition(frame_search, transitionSet)
    }

    private fun fillWeatherViews(weather: Weather) {

        val cityName = resources.getString(R.string.city_name_with_country_code,
            weather.cityName, weather.system.countryCode.capitalize())
        txv_card_city_name.text = cityName

        val temperature = resources.getString(R.string.temperature_pattern_celcius,
            weather.main.temperature.roundToInt())
        txv_card_temperature.text = temperature

        weather.conditions?.getOrNull(0)?.also {
            txv_card_weather_description.text = it.description
        }
    }

    private fun isLocationServiceAvailable(available: Boolean) {
        imv_search_by_location.visibility = if (available) View.VISIBLE else View.GONE
        val dimen = if (available) R.dimen.search_text_padding_location else R.dimen.search_text_padding_no_location
        val paddingStart = resources.getDimensionPixelSize(dimen)
        tiet_city_name.setPadding(
            paddingStart,
            tiet_city_name.paddingTop,
            tiet_city_name.paddingRight,
            tiet_city_name.paddingBottom)
    }

    private fun showKeyboard() = inputMethodManager.showSoftInput(tiet_city_name, 0)

    private fun hideKeyboard() = inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

    companion object {
        val TAG = AddWeatherFragment::class.java.simpleName
        val locationPermissionRequestCode = 200
        val locationSettingsRequestCode = 201
    }
}