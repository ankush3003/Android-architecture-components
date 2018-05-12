package weather.tutorial.com.weather.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * Created by ankush3003 on 12/05/18.
 */

@Database(entities = {WeatherEntry.class}, version = 1)
@TypeConverters(DateConvertor.class)
public abstract class WeatherDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "weather";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static volatile WeatherDatabase sInstance;

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
