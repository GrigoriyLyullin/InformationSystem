package com.railwaycompany.business.services.interfaces;

import com.railwaycompany.business.dto.PassengerData;
import com.railwaycompany.business.dto.TicketData;
import com.railwaycompany.business.services.exceptions.AlreadyRegisteredException;
import com.railwaycompany.business.services.exceptions.HasNoEmptySeatsException;
import com.railwaycompany.business.services.exceptions.InvalidInputDataException;
import com.railwaycompany.business.services.exceptions.SalesStopException;

import java.util.Date;
import java.util.List;

public interface TicketService {

    TicketData buyTicket(int userId, int trainNumber, Date departureDate, String stationFromStr, PassengerData passengerData)
            throws HasNoEmptySeatsException, AlreadyRegisteredException, SalesStopException, InvalidInputDataException;

    List<TicketData> getTickets(String dateFrom, String timeFrom, String dateTo, String timeTo);

    boolean hasEmptySeats(int trainNumber, String dispatchStationName, Date departureDate) throws InvalidInputDataException;

    boolean hasEnoughTimeBeforeDeparture(int trainNumber, String dispatchStationName, Date departureDate) throws InvalidInputDataException;

    boolean isRegistered(Integer trainNumberInt, String dispatchStation, Date date, String firstName, String lastName, Date birth)
            throws InvalidInputDataException;

    Float getTicketCost(Integer trainNumberInt, String dispatchStation, Date date) throws InvalidInputDataException;
}
