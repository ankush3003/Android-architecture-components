package weather.tutorial.com.weather.ui.main;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import weather.tutorial.com.weather.data.SunshineRepository;

/**
 * Factory to create {@link MainActivityViewModel}
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
