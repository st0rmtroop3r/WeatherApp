package com.github.st0rmtroop3r.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.github.st0rmtroop3r.weather.R
import com.github.st0rmtroop3r.weather.viewmodel.MainViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MainFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel

    companion object {
        val TAG = MainFragment::class.java.simpleName
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val textView = view?.findViewById<TextView>(R.id.message)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.currentWeather.observe(this,
            Observer { weather -> textView?.text = weather.toString() })

        viewModel.currentWeatherError.observe(this,
            Observer { error -> textView?.text = "Error: ${error}" })
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}
