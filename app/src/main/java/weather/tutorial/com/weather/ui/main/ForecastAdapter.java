package weather.tutorial.com.weather.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Date;
import java.util.List;

import weather.tutorial.com.weather.R;
import weather.tutorial.com.weather.data.database.ListViewWeatherEntry;
import weather.tutorial.com.weather.data.database.WeatherEntry;
import weather.tutorial.com.weather.utils.SunshineDateUtils;
import weather.tutorial.com.weather.utils.SunshineWeatherUtils;

/**
 * Exposes a list of weather forecasts from a list of {@link WeatherEntry} to a {@link RecyclerView}.
 */
class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;

    private final Context mContext;
    private final ForecastAdapterOnItemClickHandler mClickHandler;

    // Flag to determine if we want to use a separate view for portrait and landscape mode.
    private final boolean mUseTodayLayout;
    private List<ListViewWeatherEntry> mForecast;

    /**
     * Creates ForecastAdapter.
     *
     * @param context
     * @param clickHandler
     */
    ForecastAdapter(@NonNull Context context, ForecastAdapterOnItemClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
        mUseTodayLayout = true;//mContext.getResources().getBoolean(R.bool.use_today_layout);
    }


    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int layoutId = getLayoutIdByType(viewType);
        View view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
        view.setFocusable(true);
        return new ForecastAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param forecastAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder forecastAdapterViewHolder, int position) {
        ListViewWeatherEntry currentWeather = mForecast.get(position);

        // Weather Icon
        int weatherIconId = currentWeather.getWeatherIconId();
        int weatherImageResourceId = getImageResourceId(weatherIconId, position);
        forecastAdapterViewHolder.iconView.setImageResource(weatherImageResourceId);

        // Date
        long dateInMillis = currentWeather.getDate().getTime();
        String dateString = SunshineDateUtils.getFriendlyDateString(mContext, dateInMillis, false);
        forecastAdapterViewHolder.dateView.setText(dateString);

        // Description
        String description = SunshineWeatherUtils.getStringForWeatherCondition(mContext, weatherIconId);
        String descriptionA11y = mContext.getString(R.string.a11y_forecast, description);
        forecastAdapterViewHolder.descriptionView.setText(description);
        forecastAdapterViewHolder.descriptionView.setContentDescription(descriptionA11y);

        // High max temp
        double highInCelsius = currentWeather.getMax();
        String highString = SunshineWeatherUtils.formatTemperature(mContext, highInCelsius);
        String highA11y = mContext.getString(R.string.a11y_high_temp, highString);
        forecastAdapterViewHolder.highTempView.setText(highString);
        forecastAdapterViewHolder.highTempView.setContentDescription(highA11y);

        // Low min temp
        double lowInCelsius = currentWeather.getMin();
        String lowString = SunshineWeatherUtils.formatTemperature(mContext, lowInCelsius);
        String lowA11y = mContext.getString(R.string.a11y_low_temp, lowString);
        forecastAdapterViewHolder.lowTempView.setText(lowString);
        forecastAdapterViewHolder.lowTempView.setContentDescription(lowA11y);
    }

    /**
     * Converts the weather icon id to the local image resource id. Returns the
     * correct image based on whether the forecast is for today(large image) or the future(small image).
     *
     * @param weatherIconId Open Weather icon id
     * @param position      Position in list
     * @return Drawable image resource id for weather
     */
    private int getImageResourceId(int weatherIconId, int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {

            case VIEW_TYPE_TODAY:
                return SunshineWeatherUtils
                        .getLargeArtResourceIdForWeatherCondition(weatherIconId);

            case VIEW_TYPE_FUTURE_DAY:
                return SunshineWeatherUtils
                        .getSmallArtResourceIdForWeatherCondition(weatherIconId);

            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
    }

    /**
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        if (null == mForecast) return 0;
        return mForecast.size();
    }

    /**
     * @param position index within our RecyclerView and list
     * @return the view type (today or future day)
     */
    @Override
    public int getItemViewType(int position) {
        if (mUseTodayLayout && position == 0) {
            return VIEW_TYPE_TODAY;
        } else {
            return VIEW_TYPE_FUTURE_DAY;
        }
    }

    /**
     * Swaps the list used by the ForecastAdapter for its weather data.
     *
     * @param newForecast the new list of forecasts to use as ForecastAdapter's data source
     */
    void swapForecast(final List<ListViewWeatherEntry> newForecast) {
        mForecast = newForecast;
        notifyDataSetChanged();
    }

    /**
     * Returns the the layout id depending on whether the list item is a normal item or the larger
     * "today" list item.
     *
     * @param viewType
     * @return
     */
    private int getLayoutIdByType(int viewType) {
        switch (viewType) {

            case VIEW_TYPE_TODAY: {
                return R.layout.list_item_forecast_today;
            }

            case VIEW_TYPE_FUTURE_DAY: {
                return R.layout.forecast_list_item;
            }

            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
    }

    public interface ForecastAdapterOnItemClickHandler {
        void onItemClick(Date date);
    }

    class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView iconView;

        final TextView dateView;
        final TextView descriptionView;
        final TextView highTempView;
        final TextView lowTempView;

        ForecastAdapterViewHolder(View view) {
            super(view);

            iconView = view.findViewById(R.id.weather_icon);
            dateView = view.findViewById(R.id.date);
            descriptionView = view.findViewById(R.id.weather_description);
            highTempView = view.findViewById(R.id.high_temperature);
            lowTempView = view.findViewById(R.id.low_temperature);

            view.setOnClickListener(this);
        }

        /**
         * @param v the View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Date date = mForecast.get(adapterPosition).getDate();
            mClickHandler.onItemClick(date);
        }
    }
}