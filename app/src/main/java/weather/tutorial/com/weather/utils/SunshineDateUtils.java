package weather.tutorial.com.weather.utils;

import android.content.Context;
import android.text.format.DateUtils;

import weather.tutorial.com.weather.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Class for handling date conversions that are useful for Sunshine.
 */
public final class SunshineDateUtils {

    /* Milliseconds in a day */
    public static final long DAY_IN_MILLIS = TimeUnit.DAYS.toMillis(1);

    /**
     * This method returns the number of milliseconds (UTC time) for today's date at midnight in
     * the local time zone. For example, if you live in California and the day is September 20th,
     * 2016 and it is 6:30 PM, it will return 1474329600000. Now, if you plug this number into an
     * Epoch time converter, you may be confused that it tells you this time stamp represents 8:00
     * PM on September 19th local time, rather than September 20th. We're concerned with the GMT
     * date here though, which is correct, stating September 20th, 2016 at midnight.
     *
     * @return The number of milliseconds (UTC / GMT) for today's date at midnight in the local
     * time zone
     */
    public static long getNormalizedUtcMsForToday() {

        /*
         * This number represents the number of milliseconds that have elapsed since January
         * 1st, 1970 at midnight in the GMT time zone.
         */
        long utcNowMillis = System.currentTimeMillis();

        /*
         * This TimeZone represents the device's current time zone. It provides us with a means
         * of acquiring the offset for local time from a UTC time stamp.
         */
        TimeZone currentTimeZone = TimeZone.getDefault();

        /*
         * The getOffset method returns the number of milliseconds to add to UTC time to get the
         * elapsed time since the epoch for our current time zone. We pass the current UTC time
         * into this method so it can determine changes to account for daylight savings time.
         */
        long gmtOffsetMillis = currentTimeZone.getOffset(utcNowMillis);

        /*
         * UTC time is measured in milliseconds from January 1, 1970 at midnight from the GMT
         * time zone. Depending on your time zone, the time since January 1, 1970 at midnight (GMT)
         * will be greater or smaller. This variable represents the number of milliseconds since
         * January 1, 1970 (GMT) time.
         */
        long timeSinceEpochLocalTimeMillis = utcNowMillis + gmtOffsetMillis;

        /* This method simply converts milliseconds to days, disregarding any fractional days */
        long daysSinceEpochLocal = TimeUnit.MILLISECONDS.toDays(timeSinceEpochLocalTimeMillis);

        /*
         * Finally, we convert back to milliseconds. This time stamp represents today's date at
         * midnight in GMT time. We will need to account for local time zone offsets when
         * extracting this information from the database.
         */

        return TimeUnit.DAYS.toMillis(daysSinceEpochLocal);
    }


    public static Date getNormalizedUtcDateForToday() {
        long normalizedMilli = getNormalizedUtcMsForToday();
        return new Date(normalizedMilli);
    }

    /**
     * This method returns the number of days since the epoch (January 01, 1970, 12:00 Midnight UTC)
     * in UTC time from the current date.
     *
     * @param utcDate A date in milliseconds in UTC time.
     * @return The number of days from the epoch to the date argument.
     */
    private static long elapsedDaysSinceEpoch(long utcDate) {
        return TimeUnit.MILLISECONDS.toDays(utcDate);
    }

    /**
     * This method will return the local time midnight for the provided normalized UTC date.
     *
     * @param normalizedUtcDate UTC time at midnight for a given date. This number comes from the
     *                          database
     * @return The local date corresponding to the given normalized UTC date
     */
    private static long getLocalMidnightFromNormalizedUtcDate(long normalizedUtcDate) {
        /* The timeZone object will provide us the current user's time zone offset */
        TimeZone timeZone = TimeZone.getDefault();
        long gmtOffset = timeZone.getOffset(normalizedUtcDate);
        return normalizedUtcDate - gmtOffset;
    }

    /**
     * Helper method to convert the database representation of the date to displayable format.
     *
     * @param context               Context to use for resource localization
     * @param normalizedUtcMidnight The date in milliseconds (UTC midnight)
     * @param showFullDate          Used to show a fuller-version of the date.
     * @return A user-friendly representation of the date.
     */
    public static String getFriendlyDateString(Context context, long normalizedUtcMidnight, boolean showFullDate) {
        long localDate = getLocalMidnightFromNormalizedUtcDate(normalizedUtcMidnight);
        long daysFromEpochToProvidedDate = elapsedDaysSinceEpoch(localDate);

        /*
         * As a basis for comparison, we use the number of days that have passed from the epoch
         * until today.
         */
        long daysFromEpochToToday = elapsedDaysSinceEpoch(System.currentTimeMillis());

        if (daysFromEpochToProvidedDate == daysFromEpochToToday || showFullDate) {
            String dayName = getDayName(context, localDate);
            String readableDate = getReadableDateString(context, localDate);
            if (daysFromEpochToProvidedDate - daysFromEpochToToday < 2) {
                String localizedDayName = new SimpleDateFormat("EEEE").format(localDate);
                return readableDate.replace(localizedDayName, dayName);
            } else {
                return readableDate;
            }
        } else if (daysFromEpochToProvidedDate < daysFromEpochToToday + 7) {
            /* If the input date is less than a week in the future, just return the day name. */
            return getDayName(context, localDate);
        } else {
            int flags = DateUtils.FORMAT_SHOW_DATE
                    | DateUtils.FORMAT_NO_YEAR
                    | DateUtils.FORMAT_ABBREV_ALL
                    | DateUtils.FORMAT_SHOW_WEEKDAY;

            return DateUtils.formatDateTime(context, localDate, flags);
        }
    }

    /**
     * Returns a date string in the format specified.
     *
     * @param context      Used by DateUtils to format the date in the current locale
     * @param timeInMillis Time in milliseconds since the epoch (local time)
     * @return The formatted date string
     */
    private static String getReadableDateString(Context context, long timeInMillis) {
        int flags = DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_NO_YEAR
                | DateUtils.FORMAT_SHOW_WEEKDAY;

        return DateUtils.formatDateTime(context, timeInMillis, flags);
    }

    /**
     * Given a day, returns just the name to use for that day. E.g "today", "tomorrow", "Wednesday".
     *
     * @param context      Context to use for resource localization
     * @param dateInMillis The date in milliseconds (UTC time)
     * @return the string day of the week
     */
    private static String getDayName(Context context, long dateInMillis) {
        long daysFromEpochToProvidedDate = elapsedDaysSinceEpoch(dateInMillis);
        long daysFromEpochToToday = elapsedDaysSinceEpoch(System.currentTimeMillis());

        int daysAfterToday = (int) (daysFromEpochToProvidedDate - daysFromEpochToToday);

        switch (daysAfterToday) {
            case 0:
                return context.getString(R.string.today);
            case 1:
                return context.getString(R.string.tomorrow);

            default:
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
                return dayFormat.format(dateInMillis);
        }
    }
}