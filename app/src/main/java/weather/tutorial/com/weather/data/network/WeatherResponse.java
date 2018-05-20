package weather.tutorial.com.weather.data.network;

import android.support.annotation.NonNull;

import weather.tutorial.com.weather.data.database.WeatherEntry;


/**
 * Weather response from the backend. Contains the weather forecasts.
 */
class WeatherResponse {

    @NonNull
    private final WeatherEntry[] mWeatherForecast;

    public WeatherResponse(@NonNull final WeatherEntry[] weatherForecast) {
        mWeatherForecast = weatherForecast;
    }

    public WeatherEntry[] getWeatherForecast() {
        return mWeatherForecast;
    }
}