package weather.tutorial.com.weather.ui.detail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import weather.tutorial.com.weather.data.database.WeatherEntry;

/**
 * Created by ankush3003 on 12/05/18.
 */

public class DetailActivityViewModel extends ViewModel {

    // Weather forecast the user is looking at
    private MutableLiveData<WeatherEntry> mWeather;

    public DetailActivityViewModel() {
        mWeather = new MutableLiveData<>();
    }

    public MutableLiveData<WeatherEntry> getWeather() {
        return mWeather;
    }

    // This will notify the observer
    public void setWeather(WeatherEntry weatherEntry) {
        mWeather.postValue(weatherEntry);
    }

}
