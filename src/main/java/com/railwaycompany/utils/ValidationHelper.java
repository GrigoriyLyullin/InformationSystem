package com.railwaycompany.utils;

import java.util.regex.Pattern;

/**
 * Designed for parameters validation.
 */
public class ValidationHelper {
    /**
     * String with pattern for date checks.
     */
    private static final String DATE_PATTERN_STR = "^(19|20\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
    /**
     * String with pattern for time checks.
     */
    private static final String TIME_PATTERN_STR = "^(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$";

    /**
     * String with pattern for train number checks.
     */
    private static final String TRAIN_NUMBER_PATTERN_STR = "^[0-9]{1,9}$";
    /**
     * Pattern for date checks.
     */
    private static Pattern datePattern = Pattern.compile(DATE_PATTERN_STR);
    /**
     * Pattern for station name checks.
     */

    /**
     * Pattern for time checks.
     */
    private static Pattern timePattern = Pattern.compile(TIME_PATTERN_STR);
    /**
     * Pattern for train number checks.
     */
    private static Pattern trainNumberPattern = Pattern.compile(TRAIN_NUMBER_PATTERN_STR);


    /**
     * Checks that string contains valid date.
     *
     * @param date - string with date
     * @return True if string contains valid date, otherwise - False.
     */
    public static boolean isValidDateStr(String date) {
        return date != null && !date.isEmpty() && datePattern.matcher(date).matches();
    }

    /**
     * Checks that string contains valid station name.
     *
     * @param name - string with station name
     * @return True if string contains valid station name, otherwise - False.
     */
    public static boolean isValidStationName(String name) {
        return name != null && !name.isEmpty();
    }

    /**
     * Checks that string contains valid time.
     *
     * @param time - string with time
     * @return True if string contains valid time, otherwise - False.
     */
    public static boolean isValidTimeStr(String time) {
        return time != null && !time.isEmpty() && timePattern.matcher(time).matches();
    }

    /**
     * Checks that string contains valid train number.
     *
     * @param trainNumber - string with train number
     * @return True if string contains valid train number, otherwise - False.
     */
    public static boolean isValidTrainNumber(String trainNumber) {
        return trainNumber != null && !trainNumber.isEmpty() && trainNumberPattern.matcher(trainNumber).matches();
    }

    /**
     * Checks that string contains valid name or surname.
     *
     * @param name - string with name
     * @return True if string contains valid name or surname, otherwise - False.
     */
    public static boolean isValidPassengerNameOrSurname(String name) {
        return name != null && !name.isEmpty();
    }

    /**
     * Checks that string contains valid seats number.
     *
     * @param trainSeats - string with seats number
     * @return True if string contains valid seats number, otherwise - False.
     */
    public static boolean isValidTrainSeats(String trainSeats) {
        return isValidTrainNumber(trainSeats);
    }

    /**
     * Checks that string contains valid id.
     *
     * @param id - string with id
     * @return True if string contains valid id, otherwise - False.
     */
    public static boolean isValidId(String id) {
        return isValidTrainNumber(id);
    }
}
