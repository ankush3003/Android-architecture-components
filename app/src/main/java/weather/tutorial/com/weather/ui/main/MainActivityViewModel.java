package weather.tutorial.com.weather.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import weather.tutorial.com.weather.data.SunshineRepository;
import weather.tutorial.com.weather.data.database.ListViewWeatherEntry;
import weather.tutorial.com.weather.data.database.WeatherEntry;

/**
 * Created by ankush3003 on 17/05/18.
 */

public class MainActivityViewModel extends ViewModel {
    private final String TAG = this.getClass().getSimpleName();
    private final SunshineRepository sunshineRepository;
    private final LiveData<List<ListViewWeatherEntry>> mData;

    public MainActivityViewModel(SunshineRepository sunshineRepository) {
        this.sunshineRepository = sunshineRepository;
        mData = this.sunshineRepository.getCurrentWeatherForecasts();
    }

    public LiveData<List<ListViewWeatherEntry>> getForecast() {
        return mData;
    }
}
