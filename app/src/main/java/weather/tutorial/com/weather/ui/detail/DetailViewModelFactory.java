package weather.tutorial.com.weather.ui.detail;///*

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import java.util.Date;

import weather.tutorial.com.weather.data.SunshineRepository;

/**
 * Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link SunshineRepository} and an ID for the current
 */
public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final SunshineRepository mRepository;
    private final Date date;

    public DetailViewModelFactory(SunshineRepository repository, Date date) {
        this.mRepository = repository;
        this.date = date;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailActivityViewModel(mRepository, date);
    }
}
