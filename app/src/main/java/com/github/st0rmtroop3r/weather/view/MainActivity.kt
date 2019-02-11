package com.github.st0rmtroop3r.weather.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.github.st0rmtroop3r.weather.R

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            AddWeatherFragment.locationSettingsRequestCode -> onLocationSettingsResult(resultCode)
        }
    }

    fun showAddWeatherFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, AddWeatherFragment(), AddWeatherFragment.TAG)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
    }

    private fun onLocationSettingsResult(resultCode: Int) {
        val fragment = supportFragmentManager.findFragmentByTag(AddWeatherFragment.TAG)
        fragment?.let {
            (it as AddWeatherFragment).onLocationSettingsResult(resultCode)
        }
    }

}
