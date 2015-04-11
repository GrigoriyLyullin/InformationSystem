package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.ScheduleData;
import com.railwaycompany.business.services.exceptions.StationDoesNotExistException;
import com.railwaycompany.business.services.exceptions.SuchScheduleExistException;
import com.railwaycompany.business.services.exceptions.TrainDoesNotExistException;
import com.railwaycompany.business.services.interfaces.ScheduleService;
import com.railwaycompany.persistence.dao.interfaces.ScheduleDao;
import com.railwaycompany.persistence.dao.interfaces.StationDao;
import com.railwaycompany.persistence.dao.interfaces.TrainDao;
import com.railwaycompany.persistence.entities.Schedule;
import com.railwaycompany.persistence.entities.Station;
import com.railwaycompany.persistence.entities.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleDao scheduleDao;
    @Autowired
    private StationDao stationDao;
    @Autowired
    private TrainDao trainDao;

    @Override
    public List<ScheduleData> getSchedule(Station station) {

        List<ScheduleData> scheduleDataList = null;

        if (station != null) {
            List<Schedule> schedulesByStationId = scheduleDao.getSchedulesByStationId(station.getId());
            if (schedulesByStationId != null) {
                scheduleDataList = new ArrayList<>();
                if (!schedulesByStationId.isEmpty()) {
                    for (Schedule s : schedulesByStationId) {
                        Train train = s.getTrain();
                        ScheduleData scheduleData = new ScheduleData();
                        scheduleData.setTrainId(train.getId());
                        scheduleData.setTrainNumber(train.getNumber());
                        scheduleData.setTimeArrival(s.getTimeArrival());
                        scheduleData.setTimeDeparture(s.getTimeDeparture());
                        scheduleDataList.add(scheduleData);
                    }
                }
            }
        }
        return scheduleDataList;
    }

    @Override
    public List<ScheduleData> getSchedule(String stationName) {
        Station station = stationDao.getStation(stationName);
        return getSchedule(station);
    }

    @Override
    public List<ScheduleData> getSchedule(Station stationFrom, Station stationTo, Date dateFrom, Date
            dateTo) {

        List<Schedule> schedulesFrom = scheduleDao.getSchedules(stationFrom.getId(), dateFrom);
        List<Schedule> schedulesTo = scheduleDao.getSchedules(dateTo, stationTo.getId());

        List<ScheduleData> scheduleDataList = null;

        if (schedulesFrom != null && schedulesTo != null && dateFrom != null && dateTo != null && !schedulesFrom
                .isEmpty() && !schedulesTo.isEmpty()) {
            scheduleDataList = new ArrayList<>();

            Iterator<Schedule> fromIterator = schedulesFrom.iterator();
            Iterator<Schedule> toIterator = schedulesTo.iterator();

            while (fromIterator.hasNext()) {
                Schedule sFrom = fromIterator.next();
                if (toIterator.hasNext()) {
                    Schedule sTo = toIterator.next();
                    Train sFromTrain = sFrom.getTrain();
                    Train sToTrain = sTo.getTrain();
                    if (sFromTrain.getId() == sToTrain.getId()) {
                        Date timeArrival = sTo.getTimeArrival();
                        Date timeDeparture = sFrom.getTimeDeparture();
                        if (timeDeparture.getTime() < timeArrival.getTime()) {
                            ScheduleData scheduleData = new ScheduleData();
                            scheduleData.setTrainId(sFromTrain.getId());
                            scheduleData.setTrainNumber(sFromTrain.getNumber());
                            scheduleData.setTimeDeparture(timeArrival);
                            scheduleData.setTimeArrival(timeDeparture);
                            scheduleDataList.add(scheduleData);
                        }
                    }
                }
            }
        }
        return scheduleDataList;
    }

    @Override
    public List<ScheduleData> getSchedule(String stationFromName, String stationToName, Date dateFrom) {
        List<ScheduleData> scheduleDataList = null;
        Station stationFrom = stationDao.getStation(stationFromName);
        Station stationTo = stationDao.getStation(stationToName);
        if (stationFrom != null && stationTo != null) {
            scheduleDataList = scheduleDao.getSchedules(stationFrom.getId(), stationTo.getId(), dateFrom);
        }
        return scheduleDataList;
    }

    @Override
    public List<ScheduleData> getSchedule(String stationFromName, String stationToName, Date dateFrom,
                                          int stepSize, int startNumber) {
        List<ScheduleData> scheduleDataList = null;
        Station stationFrom = stationDao.getStation(stationFromName);
        Station stationTo = stationDao.getStation(stationToName);
        if (stationFrom != null && stationTo != null) {
            scheduleDataList = scheduleDao.getSchedules(stationFrom.getId(), stationTo.getId(), dateFrom);

            Collections.sort(scheduleDataList, new Comparator<ScheduleData>() {
                @Override
                public int compare(ScheduleData o1, ScheduleData o2) {
                    if (o1.getTimeDeparture().getTime() > o2.getTimeDeparture().getTime()) {
                        return 1;
                    } else if (o1.getTimeDeparture().getTime() < o2.getTimeDeparture().getTime()) {
                        return -1;
                    }
                    return 0;
                }
            });

            List<ScheduleData> tmp = new ArrayList<>();

            for (int s = startNumber; s < startNumber + stepSize; s++) {
                if (s < scheduleDataList.size()) {
                    tmp.add(scheduleDataList.get(s));
                }
            }
            scheduleDataList = tmp;
        }
        return scheduleDataList;
    }

    @Override
    public void addSchedule(int stationId, int trainId, Date arrivalDate, Date departureDate)
            throws SuchScheduleExistException, TrainDoesNotExistException, StationDoesNotExistException {
        Station station = stationDao.read(stationId);
        if (station != null) {
            Train train = trainDao.read(trainId);
            if (train != null) {
                Schedule schedule = scheduleDao.getSchedule(stationId, trainId, arrivalDate, departureDate);
                if (schedule == null) {
                    Schedule newSchedule = new Schedule();
                    newSchedule.setStation(station);
                    newSchedule.setTrain(train);
                    newSchedule.setTimeArrival(arrivalDate);
                    newSchedule.setTimeDeparture(departureDate);
                    scheduleDao.create(newSchedule);
                } else {
                    throw new SuchScheduleExistException();
                }
            } else {
                throw new TrainDoesNotExistException();
            }
        } else {
            throw new StationDoesNotExistException();
        }
    }

    @Override
    public List<ScheduleData> getSchedule(String stationName, Date startDate) {
        List<ScheduleData> scheduleDataList = null;
        List<ScheduleData> schedule = getSchedule(stationName);
        if (schedule != null) {
            scheduleDataList = new ArrayList<>();
            for (ScheduleData s : schedule) {
                if (s.getTimeDeparture().getTime() > startDate.getTime()) {
                    scheduleDataList.add(s);
                }
            }
        }
        return scheduleDataList;
    }

    @Override
    public List<ScheduleData> getSchedule(String stationName, Date startDate, int stepSize, int startNumber) {
        List<ScheduleData> scheduleDataList = null;
        List<ScheduleData> schedule = getSchedule(stationName);
        if (schedule != null) {
            scheduleDataList = new ArrayList<>();
            for (ScheduleData s : schedule) {
                if (s.getTimeDeparture().getTime() > startDate.getTime()) {
                    scheduleDataList.add(s);
                }
            }

            Collections.sort(scheduleDataList, new Comparator<ScheduleData>() {
                @Override
                public int compare(ScheduleData o1, ScheduleData o2) {
                    if (o1.getTimeDeparture().getTime() > o2.getTimeDeparture().getTime()) {
                        return 1;
                    } else if (o1.getTimeDeparture().getTime() < o2.getTimeDeparture().getTime()) {
                        return -1;
                    }
                    return 0;
                }
            });

            List<ScheduleData> tmp = new ArrayList<>();

            for (int s = startNumber; s < startNumber + stepSize; s++) {
                if (s < scheduleDataList.size()) {
                    tmp.add(scheduleDataList.get(s));
                }
            }
            scheduleDataList = tmp;
        }
        return scheduleDataList;
    }

    @Override
    public List<ScheduleData> getSchedule(Station stationFrom, Station stationTo, Date dateFrom) {

        List<Schedule> schedulesFrom = scheduleDao.getSchedules(stationFrom.getId(), dateFrom);
        if (schedulesFrom != null && !schedulesFrom.isEmpty()) {
            Schedule first = schedulesFrom.get(0);
            schedulesFrom = new ArrayList<>();
            schedulesFrom.add(first);
        }
        List<Schedule> schedulesTo = scheduleDao.getSchedules(stationTo.getId(), dateFrom);

        List<ScheduleData> scheduleDataList = null;

        if (schedulesFrom != null && schedulesTo != null && dateFrom != null && !schedulesFrom.isEmpty() &&
                !schedulesTo.isEmpty()) {

            scheduleDataList = new ArrayList<>();

            Iterator<Schedule> fromIterator = schedulesFrom.iterator();
            Iterator<Schedule> toIterator = schedulesTo.iterator();

            while (fromIterator.hasNext()) {
                Schedule sFrom = fromIterator.next();
                if (toIterator.hasNext()) {
                    Schedule sTo = toIterator.next();
                    Train sFromTrain = sFrom.getTrain();
                    Train sToTrain = sTo.getTrain();
                    if (sFromTrain.getId() == sToTrain.getId()) {

                        Date timeArrival = sTo.getTimeArrival();
                        Date timeDeparture = sFrom.getTimeDeparture();
                        if (timeDeparture.getTime() < timeArrival.getTime()) {
                            ScheduleData scheduleData = new ScheduleData();
                            scheduleData.setTrainId(sFromTrain.getId());
                            scheduleData.setTrainNumber(sFromTrain.getNumber());
                            scheduleData.setTimeDeparture(timeArrival);
                            scheduleData.setTimeArrival(timeDeparture);
                            scheduleDataList.add(scheduleData);
                        }
                    }
                }
            }
        }
        return scheduleDataList;
    }
}
