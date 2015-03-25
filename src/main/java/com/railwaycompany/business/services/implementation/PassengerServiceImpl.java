package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.PassengerData;
import com.railwaycompany.business.services.interfaces.PassengerService;
import com.railwaycompany.persistence.dao.interfaces.TicketDao;
import com.railwaycompany.persistence.entities.Passenger;
import com.railwaycompany.persistence.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private TicketDao ticketDao;

    public PassengerServiceImpl() {
    }

    @Override
    public List<PassengerData> getAllPassengersByTrainId(int trainId) {
        List<PassengerData> passengerDataList = null;
        List<Ticket> tickets = ticketDao.getTicketsByTrainId(trainId);
        if (tickets != null && !tickets.isEmpty()) {
            passengerDataList = new ArrayList<>();
            for (Ticket ticket : tickets) {
                Passenger passenger = ticket.getPassenger();
                PassengerData passengerData = new PassengerData();
                passengerData.setId(passenger.getId());
                passengerData.setName(passenger.getName());
                passengerData.setSurname(passenger.getSurname());
                passengerData.setBirthdate(passenger.getBirthdate());
                passengerDataList.add(passengerData);
            }
        }
        return passengerDataList;
    }
}
