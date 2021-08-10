package weather.tutorial.com.weather.data.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

/**
 * {@link Dao} provides APIs for all data operations.
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

    @Query("SELECT id, weatherIconId, date, min, max from weather WHERE date >= :date")
    LiveData<List<ListViewWeatherEntry>> getCurrentWeatherForecasts (Date date);
}
