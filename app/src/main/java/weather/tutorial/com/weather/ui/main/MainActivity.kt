package weather.tutorial.com.weather.ui.main

import androidx.appcompat.app.AppCompatActivity
import weather.tutorial.com.weather.ui.main.ForecastAdapter.ForecastAdapterOnItemClickHandler
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import weather.tutorial.com.weather.R
import androidx.recyclerview.widget.LinearLayoutManager
import weather.tutorial.com.weather.data.database.ListViewWeatherEntry
import weather.tutorial.com.weather.utils.InjectorUtils
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import weather.tutorial.com.weather.ui.detail.DetailActivity
import java.util.*

/**
 * Displays Weather data overview (with min - max temp) for future 14 days
 */
class MainActivity : AppCompatActivity(), ForecastAdapterOnItemClickHandler {
    // Weather data list items
    private var mRecyclerView: RecyclerView? = null
    private var mPosition = RecyclerView.NO_POSITION
    private var mLoadingIndicator: ProgressBar? = null
    private var mForecastAdapter: ForecastAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.recyclerview_forecast)
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator)
        mForecastAdapter = ForecastAdapter(this, this)

        mRecyclerView?.let {
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            it.setLayoutManager(layoutManager)
            it.setHasFixedSize(true)
            it.setAdapter(mForecastAdapter)
        }

        // Get ViewModel and start observing data.
        viewModel.forecast.observe(this, { weatherEntries: List<ListViewWeatherEntry?>? ->
            // refresh list
            mForecastAdapter!!.swapForecast(weatherEntries)
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0
            mRecyclerView?.smoothScrollToPosition(mPosition)

            // Show the weather list or the loading screen based on whether the forecast data exists
            // and is loaded
            if (weatherEntries != null && weatherEntries.size != 0) {
                showWeatherDataView()
            } else {
                showLoading()
            }
        })
    }

    /**
     * Gets ViewModel from MainViewModelFactory instantiated using dependency injection
     *
     * @return MainActivityViewModel
     */
    private val viewModel: MainActivityViewModel
        private get() {
            val factory = InjectorUtils.provideMainActivityViewModelFactory(this)
            return ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)
        }

    /**
     * Handles onclick - open detail activity
     *
     * @param date
     */
    override fun onItemClick(date: Date) {
        val weatherDetailIntent = Intent(this@MainActivity, DetailActivity::class.java)
        val timestamp = date.time
        weatherDetailIntent.putExtra(DetailActivity.WEATHER_ID_EXTRA, timestamp)
        startActivity(weatherDetailIntent)
    }

    /**
     * Hides loading indicator and shows weather list.
     */
    private fun showWeatherDataView() {
        mLoadingIndicator!!.visibility = View.INVISIBLE
        mRecyclerView!!.visibility = View.VISIBLE
    }

    /**
     * Shows loading indicator while data is unavailable.
     */
    private fun showLoading() {
        mRecyclerView!!.visibility = View.INVISIBLE
        mLoadingIndicator!!.visibility = View.VISIBLE
    }
}