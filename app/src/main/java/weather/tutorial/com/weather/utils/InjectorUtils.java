package weather.tutorial.com.weather.utils;

import android.content.Context;
import android.util.Log;


import java.util.Date;

import weather.tutorial.com.weather.AppExecutors;
import weather.tutorial.com.weather.data.SunshineRepository;
import weather.tutorial.com.weather.data.database.WeatherDatabase;
import weather.tutorial.com.weather.data.network.WeatherNetworkDataSource;
import weather.tutorial.com.weather.ui.detail.DetailViewModelFactory;
import weather.tutorial.com.weather.ui.main.MainViewModelFactory;

/**
 * Provides static methods to inject the various classes needed for Sunshine
 */
public class InjectorUtils {
    private final static String TAG = "InjectorUtils";

    public static SunshineRepository provideRepository(Context context) {
        Log.d(TAG, "-------------->provideRepository");
        WeatherDatabase database = WeatherDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        WeatherNetworkDataSource networkDataSource =
                WeatherNetworkDataSource.getInstance(context.getApplicationContext(), executors);
        return SunshineRepository.getInstance(database.weatherDao(), networkDataSource, executors);
    }

    public static WeatherNetworkDataSource provideNetworkDataSource(Context context) {
        Log.d(TAG, "-------------->provideNetworkDataSource");
        AppExecutors executors = AppExecutors.getInstance();
        return WeatherNetworkDataSource.getInstance(context.getApplicationContext(), executors);
    }

    public static DetailViewModelFactory provideDetailViewModelFactory(Context context, Date date) {
        SunshineRepository repository = provideRepository(context.getApplicationContext());
        Log.d(TAG, "-------------->provideDetailViewModelFactory");
        return new DetailViewModelFactory(repository, date);
    }

    public static MainViewModelFactory provideMainActivityViewModelFactory(Context context) {
        SunshineRepository repository = provideRepository(context.getApplicationContext());
        return new MainViewModelFactory(repository);
    }

}