package weather.tutorial.com.weather.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import weather.tutorial.com.weather.data.SunshineRepository;
import weather.tutorial.com.weather.data.database.ListViewWeatherEntry;

/**
 * ViewModel for {@link MainActivity}
 */
public class MainActivityViewModel extends ViewModel {
    private final String TAG = this.getClass().getSimpleName();
    private final LiveData<List<ListViewWeatherEntry>> mData;

    public MainActivityViewModel(SunshineRepository sunshineRepository) {
        mData = sunshineRepository.getCurrentWeatherForecasts();
    }

    public LiveData<List<ListViewWeatherEntry>> getForecast() {
        return mData;
    }
}
