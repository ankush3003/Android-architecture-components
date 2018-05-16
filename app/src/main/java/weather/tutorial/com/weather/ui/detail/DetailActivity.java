package weather.tutorial.com.weather.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

import weather.tutorial.com.weather.R;
import weather.tutorial.com.weather.data.database.WeatherEntry;
import weather.tutorial.com.weather.utils.InjectorUtils;
import weather.tutorial.com.weather.utils.SunshineDateUtils;

public class DetailActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    DetailActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        mViewModel = getViewModel();
        Log.d(TAG, "-------------->mViewModel: " + mViewModel.toString());
        mViewModel.getWeather().observe(this, weatherEntry -> {
            Log.d(TAG, "-------------->weatherEntry: " + weatherEntry);
            if (weatherEntry != null)
             bindDataToUI(weatherEntry);
        });
    }

    private DetailActivityViewModel getViewModel() {
        Log.d(TAG, "-------------->getViewModel()");
        Date date = SunshineDateUtils.getNormalizedUtcDateForToday();
        Log.d(TAG, "-------------->date " + date.toString());
        DetailViewModelFactory viewModelFactory = InjectorUtils.provideDetailViewModelFactory(this, date);
        return ViewModelProviders.of(this, viewModelFactory).get(DetailActivityViewModel.class);
    }

    private void bindDataToUI(WeatherEntry weatherEntry) {
        Log.d(TAG, "--------------> Data: " + weatherEntry.toString());
        Toast.makeText(this, "MAX: " + weatherEntry.getMax(), Toast.LENGTH_LONG).show();
    }

}
