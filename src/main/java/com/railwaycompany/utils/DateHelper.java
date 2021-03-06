package com.railwaycompany.utils;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
     * of milliseconds in 1 week.
     */
    public static final long MILLIS_IN_WEEK = MILLIS_IN_DAY * 7;
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
     * Converts string with date to Date object.
     *
     * @param dateStr - string with date
     * @return Date object or null if cannot convert this string to Date.
     */
    public static Date convertDate(final String dateStr) {
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            LOG.warn("Cannot parse date string \"" + dateStr + "\"", e);
        } catch (NumberFormatException e) {
            LOG.warn("dateStr: " + dateStr, e);
        } catch (Exception e) {
            LOG.warn(e);
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
            SimpleDateFormat datetimeFormat = new SimpleDateFormat(DATETIME_PATTERN);
            datetime = datetimeFormat.parse(datetimeStr);
        } catch (ParseException e) {
            LOG.warn("Cannot parse datetime string \"" + datetimeStr + "\"", e);
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

    /**
     * Converts string with date and time into Date object.
     *
     * @param date- string with date
     * @param time- string with time
     * @return Date object or null if cannot convert this strings into Date.
     */
    public static Date convertDatetime(String date, String time) {
        return convertDatetime(date + " " + time);
    }
}
