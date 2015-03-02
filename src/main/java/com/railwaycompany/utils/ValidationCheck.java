package com.railwaycompany.utils;

import java.util.regex.Pattern;

public class ValidationCheck {

    private static final String DATE_PATTERN_STR = "^(20\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";

    private static final String TIME_PATTERN_STR = "(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])";

    private static final String STATION_NAME_PATTERN_STR = "^[a-zA-Zа-яА-Я]+[- ]*[a-zA-Zа-яА-Я]+$";

    private static Pattern datePattern = Pattern.compile(DATE_PATTERN_STR);

    private static Pattern stationNamePattern = Pattern.compile(STATION_NAME_PATTERN_STR);
    private static Pattern timePattern = Pattern.compile(TIME_PATTERN_STR);

    public static boolean isValidDateStr(String date) {
        return date != null && !date.isEmpty() && datePattern.matcher(date).matches();
    }

    public static boolean isValidStationName(String name) {
        return name != null && !name.isEmpty() && stationNamePattern.matcher(name).matches();
    }

    public static boolean isValidTimeStr(String time) {
        return time != null && !time.isEmpty() && timePattern.matcher(time).matches();
    }
}
