package weather.tutorial.com.weather.ui.detail;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import java.util.Date;

import weather.tutorial.com.weather.R;
import weather.tutorial.com.weather.data.database.WeatherEntry;
import weather.tutorial.com.weather.databinding.ActivityDetailBinding;
import weather.tutorial.com.weather.utils.InjectorUtils;
import weather.tutorial.com.weather.utils.SunshineDateUtils;
import weather.tutorial.com.weather.utils.SunshineWeatherUtils;

public class DetailActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    public static final String WEATHER_ID_EXTRA = "WEATHER_ID_EXTRA";

    // This field is used for data binding.
    private ActivityDetailBinding mDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        getViewModel().getWeather().observe(this, weatherEntry -> {
            if (weatherEntry != null)
             bindDataToUI(weatherEntry);
        });
    }

    private DetailActivityViewModel getViewModel() {
        long timestamp = getIntent().getLongExtra(WEATHER_ID_EXTRA, -1);
        Date date = new Date(timestamp);
        Log.d(TAG, "date: " + date.toString());
        DetailViewModelFactory viewModelFactory = InjectorUtils.provideDetailViewModelFactory(this, date);
        return ViewModelProviders.of(this, viewModelFactory).get(DetailActivityViewModel.class);
    }

    private void bindDataToUI(WeatherEntry weatherEntry) {
        // Weather icon
        int weatherId = weatherEntry.getWeatherIconId();
        int weatherImageId = SunshineWeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId);

        /* Set the resource ID on the icon to display the art */
        mDetailBinding.primaryInfo.weatherIcon.setImageResource(weatherImageId);

        /*
         * When displaying this date, one must add the GMT offset (in milliseconds) to acquire
         * the date representation for the local date in local time.
         * SunshineDateUtils#getFriendlyDateString takes care of this for us.
         */
        long localDateMidnightGmt = weatherEntry.getDate().getTime();
        String dateText = SunshineDateUtils.getFriendlyDateString(DetailActivity.this, localDateMidnightGmt, true);
        mDetailBinding.primaryInfo.date.setText(dateText);

        /* Use the weatherId to obtain the proper description */
        String description = SunshineWeatherUtils.getStringForWeatherCondition(DetailActivity.this, weatherId);

        /* Create the accessibility (a11y) String from the weather description */
        String descriptionA11y = getString(R.string.a11y_forecast, description);

        /* Set the text and content description (for accessibility purposes) */
        mDetailBinding.primaryInfo.weatherDescription.setText(description);
        mDetailBinding.primaryInfo.weatherDescription.setContentDescription(descriptionA11y);

        /* Set the content description on the weather image (for accessibility purposes) */
        mDetailBinding.primaryInfo.weatherIcon.setContentDescription(descriptionA11y);

        double maxInCelsius = weatherEntry.getMax();

        String highString = SunshineWeatherUtils.formatTemperature(DetailActivity.this, maxInCelsius);

        /* Create the accessibility (a11y) String from the weather description */
        String highA11y = getString(R.string.a11y_high_temp, highString);

        /* Set the text and content description (for accessibility purposes) */
        mDetailBinding.primaryInfo.highTemperature.setText(highString);
        mDetailBinding.primaryInfo.highTemperature.setContentDescription(highA11y);

        double minInCelsius = weatherEntry.getMin();

        String lowString = SunshineWeatherUtils.formatTemperature(DetailActivity.this, minInCelsius);

        String lowA11y = getString(R.string.a11y_low_temp, lowString);

        /* Set the text and content description (for accessibility purposes) */
        mDetailBinding.primaryInfo.lowTemperature.setText(lowString);
        mDetailBinding.primaryInfo.lowTemperature.setContentDescription(lowA11y);

        // Humidity
        double humidity = weatherEntry.getHumidity();
        String humidityString = getString(R.string.format_humidity, humidity);
        String humidityA11y = getString(R.string.a11y_humidity, humidityString);

        /* Set the text and content description (for accessibility purposes) */
        mDetailBinding.extraDetails.humidity.setText(humidityString);
        mDetailBinding.extraDetails.humidity.setContentDescription(humidityA11y);

        mDetailBinding.extraDetails.humidityLabel.setContentDescription(humidityA11y);

        /* Read wind speed (in MPH) and direction (in compass degrees)*/
        double windSpeed = weatherEntry.getWind();
        double windDirection = weatherEntry.getDegrees();
        String windString = SunshineWeatherUtils.getFormattedWind(DetailActivity.this, windSpeed, windDirection);
        String windA11y = getString(R.string.a11y_wind, windString);

        /* Set the text and content description (for accessibility purposes) */
        mDetailBinding.extraDetails.windMeasurement.setText(windString);
        mDetailBinding.extraDetails.windMeasurement.setContentDescription(windA11y);
        mDetailBinding.extraDetails.windLabel.setContentDescription(windA11y);

        // Pressure
        double pressure = weatherEntry.getPressure();

        // Format pressure
        String pressureString = getString(R.string.format_pressure, pressure);

        String pressureA11y = getString(R.string.a11y_pressure, pressureString);

        /* Set the text and content description (for accessibility purposes) */
        mDetailBinding.extraDetails.pressure.setText(pressureString);
        mDetailBinding.extraDetails.pressure.setContentDescription(pressureA11y);
        mDetailBinding.extraDetails.pressureLabel.setContentDescription(pressureA11y);
    }

}
