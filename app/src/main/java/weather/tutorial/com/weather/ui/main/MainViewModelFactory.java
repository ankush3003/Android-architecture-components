package weather.tutorial.com.weather.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import java.util.Date;

import weather.tutorial.com.weather.data.SunshineRepository;
import weather.tutorial.com.weather.ui.detail.DetailActivityViewModel;

/**
 * Created by ankush3003 on 17/05/18.
 */

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final SunshineRepository mRepository;

    public MainViewModelFactory(SunshineRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(mRepository);
    }
}
