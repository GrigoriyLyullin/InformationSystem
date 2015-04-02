package com.railwaycompany.business.services.interfaces;

import com.railwaycompany.business.dto.PassengerData;
import com.railwaycompany.business.dto.TicketData;
import com.railwaycompany.business.services.exceptions.AlreadyRegisteredException;
import com.railwaycompany.business.services.exceptions.HasNoEmptySeatsException;
import com.railwaycompany.business.services.exceptions.InvalidInputDataException;
import com.railwaycompany.business.services.exceptions.SalesStopException;
import com.railwaycompany.persistence.entities.Ticket;

import java.util.Date;
import java.util.List;

public interface TicketService {

    TicketData buyTicket(int userId, int trainNumber, Date departureDate, String stationFromStr, PassengerData passengerData)
            throws HasNoEmptySeatsException, AlreadyRegisteredException, SalesStopException, InvalidInputDataException;

    List<Ticket> getTickets(String dateFrom, String timeFrom, String dateTo, String timeTo);
}
