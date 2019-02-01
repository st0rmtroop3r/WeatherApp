package com.github.st0rmtroop3r.weather.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.github.st0rmtroop3r.weather.R
import com.github.st0rmtroop3r.weather.WeatherApp
import com.github.st0rmtroop3r.weather.view.adapter.CurrentWeatherRecyclerAdapter
import com.github.st0rmtroop3r.weather.viewmodel.MainViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    @Inject
    lateinit var recyclerAdapter: CurrentWeatherRecyclerAdapter

    private lateinit var viewModel: MainViewModel

    companion object {
        val TAG = MainFragment::class.java.simpleName
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rcv_current_weather.layoutManager = LinearLayoutManager(WeatherApp.appContext)
        rcv_current_weather.adapter = recyclerAdapter
        rcv_current_weather.addOnScrollListener(object: OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) fab_add_weather.hide() else fab_add_weather.show()
            }
        } )

        srl_current_weather.setOnRefreshListener { updateWeatherData() }

        fab_add_weather?.setOnClickListener {
            (activity as MainActivity).showAddWeatherFragment()
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java)

        viewModel.citiesList.observe(this,
            Observer { Log.w(TAG, "citiesList.observe: $it") })

        viewModel.currentWeather.observe(this,
            Observer { recyclerAdapter.setWeatherList(it) })

        viewModel.currentWeatherError.observe(this,
            Observer { showErrorSnackbar(it) })

        viewModel.updateProgressIsVisible.observe(this, Observer {
            srl_current_weather.isRefreshing = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.mi_update_weather -> updateWeatherData()
        else -> super.onOptionsItemSelected(item)
    }

    private fun updateWeatherData(): Boolean {
        viewModel.onUpdateWeatherTriggered()
        return true
    }

    private fun showErrorSnackbar(message: String) {
        val snackbar = Snackbar.make(coordinator_layout, message, 4000)
        snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
        snackbar.setAction(getString(R.string.error_snack_action_name)) { snackbar.dismiss() }
        snackbar.show()
    }
}
