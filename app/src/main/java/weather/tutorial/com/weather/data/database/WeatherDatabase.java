package weather.tutorial.com.weather.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * {@link Database} for sunshine project.
 * Specify list of {@link TypeConverters} required.
 */
@Database(entities = {WeatherEntry.class}, version = 1)
@TypeConverters(DateConvertor.class)
public abstract class WeatherDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "weather";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static volatile WeatherDatabase sInstance;

    // Getter for Dao
    public abstract WeatherDao weatherDao();

    public static WeatherDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            WeatherDatabase.class, WeatherDatabase.DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }
}
