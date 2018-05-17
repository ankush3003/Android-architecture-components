package weather.tutorial.com.weather.data.database;

import java.util.Date;

/**
 * Simplified {@link WeatherEntry} which only contains the details needed for the weather list in
 * the {@link weather.tutorial.com.weather.ui.main.ForecastAdapter}
 * used in {@link weather.tutorial.com.weather.ui.main.MainActivity}
 */
public class ListViewWeatherEntry {

    private int id;
    private int weatherIconId;
    private Date date;
    private double min;
    private double max;

    public ListViewWeatherEntry(int id, int weatherIconId, Date date, double min, double max) {
        this.id = id;
        this.weatherIconId = weatherIconId;
        this.date = date;
        this.min = min;
        this.max = max;
    }

    public int getId() {
        return id;
    }

    public int getWeatherIconId() {
        return weatherIconId;
    }

    public Date getDate() {
        return date;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}