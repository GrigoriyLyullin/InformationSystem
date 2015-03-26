package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.PassengerData;
import com.railwaycompany.business.dto.TicketData;
import com.railwaycompany.business.services.exceptions.AlreadyRegisteredException;
import com.railwaycompany.business.services.exceptions.HasNoEmptySeatsException;
import com.railwaycompany.business.services.exceptions.InvalidInputDataException;
import com.railwaycompany.business.services.exceptions.SalesStopException;
import com.railwaycompany.business.services.interfaces.TicketService;
import com.railwaycompany.persistence.dao.interfaces.*;
import com.railwaycompany.persistence.entities.Passenger;
import com.railwaycompany.persistence.entities.Station;
import com.railwaycompany.persistence.entities.Ticket;
import com.railwaycompany.persistence.entities.Train;
import com.railwaycompany.utils.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                        passenger.setUser(userDao.read(userId));
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
                            LOG.warning(message);
                            throw new SalesStopException(message);
                        }

                    } else {
                        String message = "Passenger: " + passengerData + " already registered on train with id: " + trainId;
                        LOG.warning(message);
                        throw new AlreadyRegisteredException(message);
                    }

                } else {
                    String message = "Train with id: " + trainId + " has " + seats + " seats, " + countOfTickets + " " +
                            "tickets has been sold.";
                    LOG.warning(message);
                    throw new HasNoEmptySeatsException(message);
                }
            } else {
                String msg = "Train not found exception. trainNumber: " + trainNumber + " departureDate: " +
                        departureDate;
                LOG.warning(msg);
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
            LOG.log(Level.WARNING, msg.toString(), e);
            throw new InvalidInputDataException(msg.toString(), e);
        }
    }

    @Override
    public List<TicketData> getAll() {
        List<TicketData> ticketDataList = new ArrayList<>();
        List<Ticket> ticketList = ticketDao.getAll();
        for (Ticket t : ticketList) {
            TicketData ticketData = new TicketData();
            Train train = t.getTrain();
            ticketData.setTrainNumber(train.getNumber());
            Passenger p = t.getPassenger();
            PassengerData passengerData = new PassengerData();
            passengerData.setId(p.getId());
            passengerData.setName(p.getName());
            passengerData.setSurname(p.getSurname());
            passengerData.setBirthdate(p.getBirthdate());
            ticketData.setPassengerData(passengerData);
            ticketDataList.add(ticketData);
        }
        return ticketDataList;
    }
}
