package com.railwaycompany.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class DateHelperTest {

    @Test
    public void testConvertDate() throws Exception {

    }

    @Test
    public void testConvertDatetime() throws Exception {

    }

    @Test
    public void testIsEnoughTime() throws Exception {
        Date now = new Date();
        Assert.assertFalse(DateHelper.isEnoughTime(now, DateHelper.MILLIS_IN_TEN_MINUTES));
        Date inTenMinutes = new Date(new Date().getTime() + DateHelper.MILLIS_IN_TEN_MINUTES);
        Assert.assertTrue(DateHelper.isEnoughTime(inTenMinutes, DateHelper.MILLIS_IN_TEN_MINUTES));
        Thread.sleep(1);
        Assert.assertFalse(DateHelper.isEnoughTime(inTenMinutes, DateHelper.MILLIS_IN_TEN_MINUTES));
    }
}