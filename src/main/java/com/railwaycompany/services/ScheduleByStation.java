package com.railwaycompany.services;

import java.util.Date;

public class ScheduleByStation {

    private int trainId;
    private long trainNumber;
    private Date timeArrival;
    private Date timeDeparture;

    public ScheduleByStation() {
    }

    public long getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(long trainNumber) {
        this.trainNumber = trainNumber;
    }

    public Date getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(Date timeArrival) {
        this.timeArrival = timeArrival;
    }

    public Date getTimeDeparture() {
        return timeDeparture;
    }

    public void setTimeDeparture(Date timeDeparture) {
        this.timeDeparture = timeDeparture;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public int getTrainId() {
        return trainId;
    }
}
