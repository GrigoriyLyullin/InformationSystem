package com.railwaycompany.business.services.interfaces;

import com.railwaycompany.business.dto.PassengerData;
import com.railwaycompany.business.services.exceptions.AlreadyRegisteredException;
import com.railwaycompany.business.services.exceptions.HasNoEmptySeatsException;
import com.railwaycompany.business.services.exceptions.InvalidInputDataException;
import com.railwaycompany.business.services.exceptions.SalesStopException;

import java.util.Date;

public interface TicketService {

    void buyTicket(int userId, int trainNumber, Date departureDate, String stationFromStr, PassengerData passengerData)
            throws HasNoEmptySeatsException, AlreadyRegisteredException, SalesStopException, InvalidInputDataException;
}
