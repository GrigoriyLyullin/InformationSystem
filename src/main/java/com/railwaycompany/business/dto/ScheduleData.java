package com.railwaycompany.business.dto;

import java.util.Date;
import java.util.List;

public class ScheduleData {

    private int trainId;
    private long trainNumber;
    private Date timeArrival;
    private Date timeDeparture;
    private List<StationData> stationList;

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

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public List<StationData> getStationList() {
        return stationList;
    }

    public void setStationList(List<StationData> stationList) {
        this.stationList = stationList;
    }
}
