package weather.tutorial.com.weather.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;


import java.util.Date;

import weather.tutorial.com.weather.data.SunshineRepository;
import weather.tutorial.com.weather.data.database.WeatherEntry;

/**
 * Created by ankush3003 on 12/05/18.
 */

public class DetailActivityViewModel extends ViewModel {
    private final String TAG = this.getClass().getSimpleName();
    // Weather forecast the user is looking at
    private LiveData<WeatherEntry> mWeather;

    private SunshineRepository repository;
    private Date date;

    public DetailActivityViewModel() {
        Log.d(TAG, "-------------->DetailActivityViewModel def constructor");
        mWeather = new MutableLiveData<>();
    }

    public DetailActivityViewModel(SunshineRepository repository, Date date) {
        this.repository = repository;
        this.date = date;

        mWeather = repository.getWeatherByDate(date);
        Log.d(TAG, "-------------->DetailActivityViewModel param constructor");
    }

    public LiveData<WeatherEntry> getWeather() {
        return mWeather;
    }

    // postVlaue will trigger all observers of LiveData
//    public void setWeather(WeatherEntry weatherEntry) {
//        mWeather.postValue(weatherEntry);
//    }

}
