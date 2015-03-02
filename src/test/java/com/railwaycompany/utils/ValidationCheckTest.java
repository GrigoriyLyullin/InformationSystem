package com.railwaycompany.utils;

import org.junit.Assert;
import org.junit.Test;

public class ValidationCheckTest {

    @Test
    public void testIsValidDateStr() throws Exception {
        // Invalid date strings
        Assert.assertFalse(ValidationCheck.isValidDateStr(null));
        Assert.assertFalse(ValidationCheck.isValidDateStr(""));
        Assert.assertFalse(ValidationCheck.isValidDateStr(" "));
        Assert.assertFalse(ValidationCheck.isValidDateStr("is_not_date_string"));

        Assert.assertFalse(ValidationCheck.isValidDateStr("2015-02-00"));
        Assert.assertFalse(ValidationCheck.isValidDateStr("2015-00-01"));
        Assert.assertFalse(ValidationCheck.isValidDateStr("0000-02-01"));
        Assert.assertFalse(ValidationCheck.isValidDateStr("2015-02-32"));
        Assert.assertFalse(ValidationCheck.isValidDateStr("2015-13-01"));

        // Valid date strings
        Assert.assertTrue(ValidationCheck.isValidDateStr("2015-03-01"));
        Assert.assertTrue(ValidationCheck.isValidDateStr("2014-02-28"));
    }

    @Test
    public void testIsValidStationName() throws Exception {
        // Invalid station name strings
        Assert.assertFalse(ValidationCheck.isValidStationName(null));
        Assert.assertFalse(ValidationCheck.isValidStationName(""));
        Assert.assertFalse(ValidationCheck.isValidStationName(" "));
        Assert.assertFalse(ValidationCheck.isValidStationName("     "));

        // Valid station name strings
        Assert.assertTrue(ValidationCheck.isValidStationName("Kazan"));
        Assert.assertTrue(ValidationCheck.isValidStationName("Kazan-Pass"));
        Assert.assertTrue(ValidationCheck.isValidStationName("Kazan - Pass"));
        Assert.assertTrue(ValidationCheck.isValidStationName("Kazan Pass"));
        Assert.assertTrue(ValidationCheck.isValidStationName("Казань"));
        Assert.assertTrue(ValidationCheck.isValidStationName("Казань-Пасс"));
        Assert.assertTrue(ValidationCheck.isValidStationName("Казань Пасс"));
    }

    @Test
    public void testIsValidTimeStr() throws Exception {
        // Invalid time strings
        Assert.assertFalse(ValidationCheck.isValidTimeStr(null));
        Assert.assertFalse(ValidationCheck.isValidTimeStr(""));
        Assert.assertFalse(ValidationCheck.isValidTimeStr(" "));
        Assert.assertFalse(ValidationCheck.isValidTimeStr("not_valid_time_string"));
        Assert.assertFalse(ValidationCheck.isValidTimeStr("24:00"));
        Assert.assertFalse(ValidationCheck.isValidTimeStr("10:60"));

        // Valid time strings
        Assert.assertTrue(ValidationCheck.isValidTimeStr("00:00"));
        Assert.assertTrue(ValidationCheck.isValidTimeStr("23:59"));
        Assert.assertTrue(ValidationCheck.isValidTimeStr("11:11"));
    }
}