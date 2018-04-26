package com.railwaycompany.utils;

import java.util.regex.Pattern;

/**
 * Designed for parameters validation.
 */
public class ValidationHelper {
    /**
     * String with pattern for date checks.
     */
    private static final String DATE_PATTERN_STR = "^(19|20)\\d\\d-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
    /**
     * String with pattern for time checks.
     */
    private static final String TIME_PATTERN_STR = "^(0?[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$";
    /**
     * String with pattern for station name checks.
     */
    private static final String STATION_NAME_PATTERN_STR = "^[a-zA-Zа-яА-Я]+[- ]*[a-zA-Zа-яА-Я]+$";
    /**
     * String with pattern for name checks.
     */
    private static final String NAME_PATTERN_STR = "^[a-zA-Zа-яА-Я]+$";
    /**
     * String with pattern for train number checks.
     */
    private static final String TRAIN_NUMBER_PATTERN_STR = "^[0-9]{1,9}$";
    /**
     * String with pattern for login checks.
     */
    private static final String LOGIN_PATTERN_STR = "^[a-zA-Z0-9]*$";
    /**
     * String with pattern for password checks.
     */
    private static final String PASSWORD_PATTERN_STR = "^[a-zA-Z0-9]*$";
    /**
     * Pattern for date checks.
     */
    private static Pattern datePattern = Pattern.compile(DATE_PATTERN_STR);
    /**
     * Pattern for station name checks.
     */
    private static Pattern stationNamePattern = Pattern.compile(STATION_NAME_PATTERN_STR);
    /**
     * Pattern for time checks.
     */
    private static Pattern timePattern = Pattern.compile(TIME_PATTERN_STR);
    /**
     * Pattern for train number checks.
     */
    private static Pattern trainNumberPattern = Pattern.compile(TRAIN_NUMBER_PATTERN_STR);
    /**
     * Pattern for login checks.
     */
    private static Pattern loginPattern = Pattern.compile(LOGIN_PATTERN_STR);
    /**
     * Pattern for password checks.
     */
    private static Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN_STR);
    /**
     * Pattern for name checks.
     */
    private static Pattern namePattern = Pattern.compile(NAME_PATTERN_STR);

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
        return name != null && !name.isEmpty() && stationNamePattern.matcher(name).matches();
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
     * Checks that string contains valid login.
     *
     * @param login - string with login
     * @return True if string contains valid login, otherwise - False.
     */
    public static boolean isValidLogin(String login) {
        return login != null && !login.isEmpty() && loginPattern.matcher(login).matches();
    }

    /**
     * Checks that string contains valid password.
     *
     * @param password - string with password
     * @return True if string contains valid password, otherwise - False.
     */
    public static boolean isValidPassword(String password) {
        return password != null && !password.isEmpty() && passwordPattern.matcher(password).matches();
    }

    /**
     * Checks that string contains valid name or surname.
     *
     * @param name - string with name
     * @return True if string contains valid name or surname, otherwise - False.
     */
    public static boolean isValidPassengerNameOrSurname(String name) {
        return name != null && !name.isEmpty() && namePattern.matcher(name).matches();
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

    public static boolean isValidShortDateStr(String cardExpiresDate) {
        return cardExpiresDate != null && !cardExpiresDate.isEmpty();
    }

    public static boolean isValidCardCVC(String cardCVC) {
        return cardCVC != null && !cardCVC.isEmpty();
    }

    public static boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && !cardNumber.isEmpty();
    }

    public static boolean isValidCardHolder(String cardHolder) {
        return cardHolder != null && !cardHolder.isEmpty();
    }
}
