package com.railwaycompany.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Designed to convert strings into Date objects.
 */
public class DateHelper {

    /**
     * Number of milliseconds in one day.
     */
    public static final long MILLIS_IN_DAY = 86_400_000;
    /**
     * Number of milliseconds in 10 minutes.
     */
    public static final long MILLIS_IN_10_MINUTES = 600_000;
    /**
     * Number of milliseconds in 1 minute.
     */
    public static final long MILLIS_IN_1_MINUTE = 60_000;
    /**
     * Logger for DateHelper class.
     */
    private static final Logger LOG = Logger.getLogger(DateHelper.class.getName());
    /**
     * Date pattern for date string format.
     */
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * Date pattern for datetime string format.
     */
    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm";
    /**
     * Date formatter for date string.
     */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
    /**
     * Date formatter for date and time string.
     */
    private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(DATETIME_PATTERN);

    /**
     * Converts string with date to Date object.
     *
     * @param dateStr - string with date
     * @return Date object or null if cannot convert this string to Date.
     */
    public static Date convertDate(String dateStr) {
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            LOG.log(Level.INFO, "Cannot parse date string \"" + dateStr + "\"", e);
        }
        return date;
    }

    /**
     * Converts string with date and time into Date object.
     *
     * @param datetimeStr - string with date and time
     * @return Date object or null if cannot convert this string to Date.
     */
    public static Date convertDatetime(String datetimeStr) {
        Date datetime = null;
        try {
            datetime = datetimeFormat.parse(datetimeStr);
        } catch (ParseException e) {
            LOG.log(Level.INFO, "Cannot parse datetime string \"" + datetimeStr + "\"", e);
        }
        return datetime;
    }

    /**
     * Checks that has more or equals time to this date.
     *
     * @param date - last date
     * @param time - time that remains
     * @return True if has time to this date, otherwise - False
     */
    public static boolean isEnoughTime(Date date, Long time) {
        if (date != null && time != null) {
            long timeLeft = date.getTime() - new Date().getTime();
            return timeLeft >= time;
        }
        return false;
    }

    /**
     * Checks that has more or equals 10 minutes to this date.
     *
     * @param date - date
     * @return True if has 10 minutes to this date, otherwise - False
     */
    public static boolean hasMoreThanTenMinutes(Date date) {
        return isEnoughTime(date, MILLIS_IN_10_MINUTES);
    }
}
