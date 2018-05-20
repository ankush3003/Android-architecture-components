package weather.tutorial.com.weather.data.network;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import weather.tutorial.com.weather.utils.InjectorUtils;

/**
 * An {@link IntentService} subclass for immediately scheduling a sync with the server off of the
 * main thread. This is necessary because {@link com.firebase.jobdispatcher.FirebaseJobDispatcher}
 * will not trigger a job immediately. This should only be called when the application is on the
 * screen.
 */
public class SunshineSyncIntentService extends IntentService {
    private static final String LOG_TAG = SunshineSyncIntentService.class.getSimpleName();

    public SunshineSyncIntentService() {
        super("SunshineSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG, "onHandleIntent");
        WeatherNetworkDataSource networkDataSource = InjectorUtils.provideNetworkDataSource(this.getApplicationContext());
        networkDataSource.fetchWeather();
    }
}