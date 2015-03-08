package com.railwaycompany.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateHelper {

    public static long MILLIS_IN_DAY = 86_400_000;

    public static long MILLIS_IN_10_MINUTES = 600_000;
    public static long MILLIS_IN_1_MINUTE = 60_000;

    /**
     * Logger for DateHelper class.
     */
    private static Logger log = Logger.getLogger(DateHelper.class.getName());

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static Date convertDate(String dateStr) {
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            log.log(Level.WARNING, "Cannot parse date string \"" + dateStr + "\"", e);
        }
        return date;
    }

    public static Date convertDatetime(String datetimeStr) {
        Date datetime = null;
        try {
            datetime = datetimeFormat.parse(datetimeStr);
        } catch (ParseException e) {
            log.log(Level.WARNING, "Cannot parse datetime string \"" + datetimeStr + "\"", e);
        }
        return datetime;
    }

    public static boolean isEnoughTime(Date date, Long time) {
        if (date != null && time != null) {
            long timeLeft = date.getTime() - new Date().getTime();
            return timeLeft >= time;
        }
        return false;
    }

    public static boolean hasMoreThanTenMinutes(Date date) {
        return isEnoughTime(date, MILLIS_IN_10_MINUTES);
    }
}
