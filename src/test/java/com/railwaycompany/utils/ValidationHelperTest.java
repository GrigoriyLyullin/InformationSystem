package com.railwaycompany.utils;

import org.junit.Test;

import static com.railwaycompany.utils.ValidationHelper.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidationHelperTest {

    @Test
    public void testIsValidDateStr() throws Exception {
        assertFalse(isValidDateStr(null));
        assertFalse(isValidDateStr(""));
        assertFalse(isValidDateStr(" "));
        assertFalse(isValidDateStr("is_not_date_string"));

        assertFalse(isValidDateStr("2015-02-00"));
        assertFalse(isValidDateStr("2015-00-01"));
        assertFalse(isValidDateStr("0000-02-01"));
        assertFalse(isValidDateStr("2015-02-32"));
        assertFalse(isValidDateStr("2015-13-01"));

        assertTrue(isValidDateStr("2015-03-01"));
        assertTrue(isValidDateStr("2014-02-28"));
    }

    @Test
    public void testIsValidStationName() throws Exception {
        assertFalse(isValidStationName(null));
        assertFalse(isValidStationName(""));
        assertFalse(isValidStationName(" "));
        assertFalse(isValidStationName("     "));

        assertTrue(isValidStationName("Kazan"));
        assertTrue(isValidStationName("Kazan-Pass"));
        assertTrue(isValidStationName("Kazan - Pass"));
        assertTrue(isValidStationName("Kazan Pass"));
        assertTrue(isValidStationName("Казань"));
        assertTrue(isValidStationName("Казань-Пасс"));
        assertTrue(isValidStationName("Казань Пасс"));
    }

    @Test
    public void testIsValidTimeStr() throws Exception {
        assertFalse(isValidTimeStr(null));
        assertFalse(isValidTimeStr(""));
        assertFalse(isValidTimeStr(" "));
        assertFalse(isValidTimeStr("not_valid_time_string"));
        assertFalse(isValidTimeStr("24:00"));
        assertFalse(isValidTimeStr("10:60"));

        assertTrue(isValidTimeStr("00:00"));
        assertTrue(isValidTimeStr("23:59"));
        assertTrue(isValidTimeStr("11:11"));
    }

    @Test
    public void testIsValidTrainNumber() throws Exception {
        assertFalse(isValidTrainNumber(null));
        assertFalse(isValidTrainNumber(""));

        assertTrue(isValidTrainNumber("0"));
        assertTrue(isValidTrainNumber("123456789"));
    }


    @Test
    public void testIsValidLogin() throws Exception {
        assertFalse(isValidLogin(null));
        assertFalse(isValidLogin(""));
        assertFalse(isValidLogin("     "));

        assertTrue(isValidLogin("Login123"));
        assertTrue(isValidLogin("123456"));
        assertTrue(isValidLogin("login"));
    }

    @Test
    public void testIsValidPassword() throws Exception {
        assertFalse(isValidPassword(null));
        assertFalse(isValidPassword(""));
        assertFalse(isValidPassword("      "));

        assertTrue(isValidPassword("Password123"));
        assertTrue(isValidPassword("123456"));
        assertTrue(isValidPassword("password"));
    }
}