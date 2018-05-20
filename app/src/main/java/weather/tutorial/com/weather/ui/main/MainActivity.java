package weather.tutorial.com.weather.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Date;

import weather.tutorial.com.weather.R;
import weather.tutorial.com.weather.ui.detail.DetailActivity;
import weather.tutorial.com.weather.utils.InjectorUtils;

/**
 * Displays Weather data overview (with min - max temp) for future 14 days
 */
public class MainActivity extends AppCompatActivity implements ForecastAdapter.ForecastAdapterOnItemClickHandler{
    private final String TAG = this.getClass().getSimpleName();

    // Weather data list items
    private RecyclerView mRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;

    private ProgressBar mLoadingIndicator;
    private ForecastAdapter mForecastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_forecast);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);


        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mForecastAdapter = new ForecastAdapter(this, this);
        mRecyclerView.setAdapter(mForecastAdapter);

        // Get ViewModel and start observing data.
        getViewModel().getForecast().observe(this, weatherEntries -> {
            // refresh list
            mForecastAdapter.swapForecast(weatherEntries);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            mRecyclerView.smoothScrollToPosition(mPosition);

            // Show the weather list or the loading screen based on whether the forecast data exists
            // and is loaded
            if (weatherEntries != null && weatherEntries.size() != 0) {
                showWeatherDataView();
            } else {
                showLoading();
            }
        });

    }

    /**
     * Gets ViewModel from MainViewModelFactory instantiated using dependency injection
     *
     * @return MainActivityViewModel
     */
    private MainActivityViewModel getViewModel() {
        MainViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactory(this);
        return ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);
    }

    /**
     * Handles onclick - open detail activity
     *
     * @param date
     */
    @Override
    public void onItemClick(Date date) {
        Intent weatherDetailIntent = new Intent(MainActivity.this, DetailActivity.class);
        long timestamp = date.getTime();
        weatherDetailIntent.putExtra(DetailActivity.WEATHER_ID_EXTRA, timestamp);
        startActivity(weatherDetailIntent);
    }

    /**
     * Hides loading indicator and shows weather list.
     */
    private void showWeatherDataView() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Shows loading indicator while data is unavailable.
     */
    private void showLoading() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }
}
