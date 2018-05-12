package weather.tutorial.com.weather.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import weather.tutorial.com.weather.R;

public class DetailActivity extends AppCompatActivity {

    DetailActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mViewModel = ViewModelProviders.of(this).get(DetailActivityViewModel.class);
        mViewModel.getWeather().observe(this, weatherEntry -> {
            
        });
    }

}
