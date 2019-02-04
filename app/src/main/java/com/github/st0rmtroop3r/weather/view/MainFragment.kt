package com.github.st0rmtroop3r.weather.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.github.st0rmtroop3r.weather.R
import com.github.st0rmtroop3r.weather.WeatherApp
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.view.adapter.CurrentWeatherRecyclerAdapter
import com.github.st0rmtroop3r.weather.viewmodel.MainViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject
import kotlin.math.absoluteValue

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

        viewModel.currentWeather.observe(this,
            Observer {
                val sb = StringBuilder()
                it.forEach { sb.append(it.cityName).append(" ").append(it.system.countryCode).append(", ") }
                Log.w(TAG, "currentWeather.observe: size ${it.size}: ${sb}")
                recyclerAdapter.setWeatherList(it)
            })

        viewModel.currentWeatherError.observe(this,
            Observer { showErrorSnackbar(it) })

        viewModel.updateProgressIsVisible.observe(this, Observer {
            srl_current_weather.isRefreshing = it
        })

        ItemTouchHelper( RecyclerTouchCallback() ).attachToRecyclerView(rcv_current_weather)
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

    private fun showWeatherDeletedSnackbar(
        weather: Weather,
        delay: Int,
        adapterPosition: Int
    ) {
        val city = weather.cityName
        val country = weather.system.countryCode.capitalize()
        val message = resources.getString(R.string.snack_weather_removed_msg, city, country)
        val actionUndo = resources.getString(R.string.snack_weather_removed_btn)
        val snackbar = Snackbar.make(coordinator_layout, message, delay)
        snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
        snackbar.setAction(actionUndo) { undoDelete(weather, adapterPosition) }
        snackbar.show()
    }

    private fun undoDelete(
        weather: Weather,
        adapterPosition: Int
    ) {
        viewModel.undoDelete(weather)
        recyclerAdapter.addItem(weather, adapterPosition)
    }

    private fun onWeatherSwiped(adapterPosition: Int) {
        val weather = recyclerAdapter.getItemAt(adapterPosition)
        val delay = resources.getInteger(R.integer.delete_weather_delay)
        showWeatherDeletedSnackbar(weather, delay, adapterPosition)
        viewModel.onDeleteWeatherTriggered(weather, delay)
        recyclerAdapter.removeItem(adapterPosition)
    }

    private fun onRecyclerViewItemDropped() {
        viewModel.onWeatherListOrderChanged(recyclerAdapter.weatherList)
    }

    private inner class RecyclerTouchCallback :
        ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {

        var elevation: Float = 0f
        val idle = resources.getDimension(R.dimen.weather_card_elevation)
        val move = resources.getDimension(R.dimen.weather_card_elevation_move)

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
            if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
                elevation = idle
                onRecyclerViewItemDropped()
            } else {
                elevation = move
            }
        }

        override fun onChildDrawOver(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            viewHolder.itemView.elevation = elevation
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            recyclerAdapter.swap(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            onWeatherSwiped(viewHolder.adapterPosition)
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

            val view = viewHolder.itemView

            val background = resources.getDrawable(R.drawable.rectangle_round_corners_8dp)
            background.setBounds(view.left, view.top, view.right, view.bottom)
            var icon: Drawable? = null

            if (dX.absoluteValue > 0) {
                icon = resources.getDrawable(R.drawable.ic_delete_black_24dp)
                val iconTop = view.top + (view.height - icon.intrinsicHeight) / 2
                val iconBottom = iconTop + icon.intrinsicHeight
                val iconLeft = view.left + icon.intrinsicWidth
                val iconRight = iconLeft + icon.intrinsicWidth
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                background.setTint(resources.getColor(R.color.orange))
            } else if (dY.absoluteValue > 0) {
                background.setTint(Color.WHITE)
            }

            background.draw(c)
            icon?.draw(c)
        }
    }
}
