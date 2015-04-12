package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.PassengerData;
import com.railwaycompany.business.dto.TicketData;
import com.railwaycompany.business.services.exceptions.AlreadyRegisteredException;
import com.railwaycompany.business.services.exceptions.HasNoEmptySeatsException;
import com.railwaycompany.business.services.exceptions.InvalidInputDataException;
import com.railwaycompany.business.services.exceptions.SalesStopException;
import com.railwaycompany.business.services.interfaces.TicketService;
import com.railwaycompany.persistence.dao.interfaces.*;
import com.railwaycompany.persistence.entities.*;
import com.railwaycompany.utils.DateHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.railwaycompany.utils.ValidationHelper.isValidDateStr;
import static com.railwaycompany.utils.ValidationHelper.isValidTimeStr;

@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger LOG = Logger.getLogger(TicketServiceImpl.class.getName());

    @Autowired
    private TrainDao trainDao;

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private StationDao stationDao;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private PassengerDao passengerDao;

    @Autowired
    private UserDao userDao;

    public TicketServiceImpl() {

    }

    @Override
    public TicketData buyTicket(int userId, int trainNumber, Date departureDate, String stationName, PassengerData
            passengerData)
            throws HasNoEmptySeatsException, AlreadyRegisteredException, SalesStopException, InvalidInputDataException {

        try {
            String name = passengerData.getName();
            String surname = passengerData.getSurname();
            Date birthdate = passengerData.getBirthdate();
            Station station = stationDao.getStation(stationName);
            int stationId = station.getId();
            Integer trainId = scheduleDao.getTrainId(trainNumber, stationId, departureDate);
            if (trainId != null) {
                Train train = trainDao.read(trainId);
                int countOfTickets = ticketDao.countOfTickets(trainId);
                int seats = train.getSeats();
                if (countOfTickets < seats) {
                    Passenger passenger = passengerDao.getPassenger(name, surname, birthdate);
                    boolean newPassenger = false;
                    if (passenger == null) {
                        newPassenger = true;
                        passenger = new Passenger();
                        passenger.setName(name);
                        passenger.setSurname(surname);
                        passenger.setBirthdate(birthdate);
                        User user = userDao.read(userId);
                        List<Passenger> passengers = user.getPassengers();
                        passengers.add(passenger);
                        user.setPassengers(passengers);
                        passengerDao.create(passenger);
                    }
                    if (newPassenger || !ticketDao.hasBeenRegistered(trainId, passenger.getId())) {
                        if (DateHelper.hasMoreThanTenMinutes(departureDate)) {
                            Ticket ticket = new Ticket();
                            ticket.setTrain(train);
                            ticket.setPassenger(passenger);
                            ticketDao.create(ticket);
                            TicketData ticketData = new TicketData();
                            ticketData.setTrainNumber(trainNumber);
                            ticketData.setStationFrom(stationName);
                            ticketData.setDepartureDate(departureDate);
                            ticketData.setPassengerData(passengerData);
                            return ticketData;
                        } else {
                            String message = "Departure date " + departureDate + " in less than 10 minutes.";
                            LOG.warn(message);
                            throw new SalesStopException(message);
                        }
                    } else {
                        String message = "Passenger: " + passengerData + " already registered on train with id: " + trainId;
                        LOG.warn(message);
                        throw new AlreadyRegisteredException(message);
                    }

                } else {
                    String message = "Train with id: " + trainId + " has " + seats + " seats, " + countOfTickets + " " +
                            "tickets has been sold.";
                    LOG.warn(message);
                    throw new HasNoEmptySeatsException(message);
                }
            } else {
                String msg = "Train not found exception. trainNumber: " + trainNumber + " departureDate: " +
                        departureDate;
                LOG.warn(msg);
                throw new InvalidInputDataException(msg);
            }
        } catch (NullPointerException e) {
            StringBuilder msg = new StringBuilder("Possible that input information is not valid: ");
            if (trainNumber <= 0)
                msg.append("trainNumber: ").append(trainNumber);
            if (departureDate == null)
                msg.append("departureDate: null");
            if (stationName == null)
                msg.append("stationFrom: null");
            if (passengerData == null)
                msg.append("passengerData: null");
            else {
                if (passengerData.getName() == null)
                    msg.append("passengerData: name is null");
                if (passengerData.getSurname() == null)
                    msg.append("passengerData: surname is null");
                if (passengerData.getBirthdate() == null)
                    msg.append("passengerData: birthdate is null");
            }
            LOG.warn(msg.toString(), e);
            throw new InvalidInputDataException(msg.toString(), e);
        }
    }

    @Override
    public List<Ticket> getTickets(String dateFromStr, String timeFromStr, String dateToStr, String timeToStr) {
        List<Ticket> ticketList = null;
        if (isValidDateStr(dateFromStr) && isValidDateStr(dateToStr)
                && isValidTimeStr(timeFromStr) && isValidTimeStr(timeToStr)) {
            Date dateFrom = DateHelper.convertDatetime(dateFromStr, timeFromStr);
            Date dateTo = DateHelper.convertDatetime(dateToStr, timeToStr);
            List<Schedule> schedules = scheduleDao.getSchedules(dateFrom, dateTo);
            if (schedules != null && !schedules.isEmpty()) {
                ticketList = new ArrayList<>();
                for (Schedule schedule : schedules) {
                    List<Ticket> ticketsByTrainId = ticketDao.getTicketsByTrainId(schedule.getTrain().getId());
                    if (ticketsByTrainId != null && !ticketsByTrainId.isEmpty()) {
                        for (Ticket newTicket : ticketsByTrainId) {
                            boolean contains = false;
                            for (Ticket existTicket : ticketList) {
                                if (newTicket.getId() == existTicket.getId()) {
                                    contains = true;
                                    break;
                                }
                            }
                            if (!contains) {
                                ticketList.add(newTicket);
                            }
                        }
                    }
                }
            }
        }
        return ticketList;
    }

    @Override
    public boolean hasEmptySeats(int trainNumber, String dispatchStationName, Date departureDate)
            throws InvalidInputDataException {
        int countOfTickets;
        int seats;
        try {
            Station station = stationDao.getStation(dispatchStationName);
            int stationId = station.getId();
            Integer trainId = scheduleDao.getTrainId(trainNumber, stationId, departureDate);
            Train train = trainDao.read(trainId);
            countOfTickets = ticketDao.countOfTickets(trainId);
            seats = train.getSeats();
        } catch (NullPointerException e) {
            StringBuilder msg = new StringBuilder("Possible that input information is not valid: ");
            if (trainNumber <= 0)
                msg.append("trainNumber: ").append(trainNumber);
            if (departureDate == null)
                msg.append("departureDate: null");
            if (dispatchStationName == null)
                msg.append("stationFrom: null");
            LOG.warn(msg.toString(), e);
            throw new InvalidInputDataException(msg.toString(), e);
        }
        return countOfTickets < seats;
    }

    @Override
    public boolean hasEnoughTimeBeforeDeparture(int trainNumber, String dispatchStationName, Date departureDate)
            throws InvalidInputDataException {
        try {
            Station station = stationDao.getStation(dispatchStationName);
            int stationId = station.getId();
            Integer trainId = scheduleDao.getTrainId(trainNumber, stationId, departureDate);
            Train train = trainDao.read(trainId);

            if (train != null) {
                return DateHelper.hasMoreThanTenMinutes(departureDate);
            } else {
                throw new InvalidInputDataException("Such train does not exist");
            }
        } catch (NullPointerException e) {
            StringBuilder msg = new StringBuilder("Possible that input information is not valid: ");
            if (trainNumber <= 0)
                msg.append("trainNumber: ").append(trainNumber);
            if (departureDate == null)
                msg.append("departureDate: null");
            if (dispatchStationName == null)
                msg.append("stationFrom: null");
            LOG.warn(msg.toString(), e);
            throw new InvalidInputDataException(msg.toString(), e);
        }
    }

    @Override
    public boolean isRegistered(Integer trainNumber, String dispatchStation, Date departureDate,
                                String firstName, String lastName, Date birthdate) throws InvalidInputDataException {
        Passenger passenger = passengerDao.getPassenger(firstName, lastName, birthdate);
        if (passenger != null) {
            try {
                Station station = stationDao.getStation(dispatchStation);
                int stationId = station.getId();
                Integer trainId = scheduleDao.getTrainId(trainNumber, stationId, departureDate);
                return ticketDao.hasBeenRegistered(trainId, passenger.getId());
            } catch (NullPointerException e) {
                throw new InvalidInputDataException(e);
            }
        }
        return false;
    }

    @Override
    public Float getTicketCost(Integer trainNumber, String dispatchStation, Date date) throws InvalidInputDataException {
        try {
            Station station = stationDao.getStation(dispatchStation);
            int stationId = station.getId();
            Integer trainId = scheduleDao.getTrainId(trainNumber, stationId, date);
            Train train = trainDao.read(trainId);
            if (train != null) {
                return train.getTicketCost();
            }
        } catch (NullPointerException e) {
            throw new InvalidInputDataException(e);
        }
        return null;
    }
}
