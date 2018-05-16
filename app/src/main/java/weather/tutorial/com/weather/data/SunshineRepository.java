package weather.tutorial.com.weather.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.Date;

import weather.tutorial.com.weather.AppExecutors;
import weather.tutorial.com.weather.data.database.WeatherDao;
import weather.tutorial.com.weather.data.database.WeatherEntry;
import weather.tutorial.com.weather.data.network.WeatherNetworkDataSource;
import weather.tutorial.com.weather.utils.SunshineDateUtils;

/**
 * Handles data operations in Sunshine. Acts as a mediator between {@link WeatherNetworkDataSource}
 * and {@link WeatherDao}
 */
public class SunshineRepository {
    private static final String TAG = SunshineRepository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static SunshineRepository sInstance;
    private final WeatherDao mWeatherDao;
    private final WeatherNetworkDataSource mWeatherNetworkDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;

    private SunshineRepository(WeatherDao weatherDao,
                               WeatherNetworkDataSource weatherNetworkDataSource,
                               AppExecutors executors) {
        mWeatherDao = weatherDao;
        mWeatherNetworkDataSource = weatherNetworkDataSource;
        mExecutors = executors;

        LiveData<WeatherEntry[]> networkData = mWeatherNetworkDataSource.getCurrentWeatherForecasts();
        networkData.observeForever(newForecastsFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Delete old entries from Sunshine's database
                deleteOldData();

                // Insert our new weather data into Sunshine's database
                mWeatherDao.bulkInsert(newForecastsFromNetwork);
                Log.d(TAG, "--------> New values inserted");
            });
        });
    }

    public synchronized static SunshineRepository getInstance(
            WeatherDao weatherDao, WeatherNetworkDataSource weatherNetworkDataSource,
            AppExecutors executors) {
        Log.d(TAG, "-------> Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new SunshineRepository(weatherDao, weatherNetworkDataSource,
                        executors);
                Log.d(TAG, "-------> Made new repository");
            }
        }
        return sInstance;
    }

    /**
     * Creates periodic sync tasks and checks to see if an immediate sync is required. If an
     * immediate sync is required, this method will take care of making sure that sync occurs.
     */
    public synchronized void initializeData() {
        Log.d(TAG, "--------> initializeData()");
        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.
        if (mInitialized) return;
        mInitialized = true;

        mExecutors.diskIO().execute(() -> {//CODE ON DISK I/O THREAD HERE})}
            if (isFetchNeeded()) {
                Log.d(TAG, "--------> startFetchWeatherService()");
                startFetchWeatherService();
            } else {
                Log.d(TAG, "--------> startFetchWeatherService() NOT REQUIRED");
            }

        });

    }

    /**
     * Database related operations
     **/

    /**
     * Deletes old weather data because we don't need to keep multiple days' data
     */
    private void deleteOldData() {
        Date today = SunshineDateUtils.getNormalizedUtcDateForToday();
        mWeatherDao.deleteOldData(today);
    }

    /**
     * Checks if there are enough days of future weather for the app to display all the needed data.
     *
     * @return Whether a fetch is needed
     */
    private boolean isFetchNeeded() {
        // current Date
        Date startDate = SunshineDateUtils.getNormalizedUtcDateForToday();
        return (mWeatherDao.countAllFutureWeather(startDate) < WeatherNetworkDataSource.NUM_DAYS);
    }

    /**
     * Network related operation
     */

    private void startFetchWeatherService() {
        mWeatherNetworkDataSource.startFetchWeatherService();
    }

    public LiveData<WeatherEntry> getWeatherByDate(Date date) {
        Log.d(TAG, "--------> getWeatherByDate()");
        initializeData();
        return mWeatherDao.getWeatherByDate(date);
    }

}