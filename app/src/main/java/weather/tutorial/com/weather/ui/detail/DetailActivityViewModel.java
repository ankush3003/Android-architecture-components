package weather.tutorial.com.weather.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;

import weather.tutorial.com.weather.data.SunshineRepository;
import weather.tutorial.com.weather.data.database.WeatherEntry;

/**
 * ViewModel for {@link DetailActivity}
 */
public class DetailActivityViewModel extends ViewModel {
    private final String TAG = this.getClass().getSimpleName();
    // Weather forecast the user is looking at
    private LiveData<WeatherEntry> mWeather;

    public DetailActivityViewModel() {
        mWeather = new MutableLiveData<>();
    }

    public DetailActivityViewModel(SunshineRepository repository, Date date) {
        mWeather = repository.getWeatherByDate(date);
    }

    public LiveData<WeatherEntry> getWeather() {
        return mWeather;
    }

}
