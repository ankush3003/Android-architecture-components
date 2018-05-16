package weather.tutorial.com.weather.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.Date;


/**
 * Created by ankush3003 on 12/05/18.
 */

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(WeatherEntry... weatherEntries);

    @Query("SELECT * FROM weather WHERE date = :date")
    LiveData<WeatherEntry> getWeatherByDate(Date date);

    @Query("SELECT COUNT(*) from weather WHERE date >= :today")
    int countAllFutureWeather(Date today);

    @Query("DELETE FROM weather WHERE date < :today")
    void deleteOldData(Date today);
}
