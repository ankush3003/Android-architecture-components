package weather.tutorial.com.weather.ui.main

import weather.tutorial.com.weather.data.SunshineRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import weather.tutorial.com.weather.data.database.ListViewWeatherEntry

/**
 * ViewModel for [MainActivity]
 */
class MainActivityViewModel(sunshineRepository: SunshineRepository) : ViewModel() {
    val forecast: LiveData<List<ListViewWeatherEntry>>

    init {
        forecast = sunshineRepository.currentWeatherForecasts
    }
}