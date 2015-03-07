package com.railwaycompany.business.services.implementation;

import com.railwaycompany.persistence.dao.interfaces.TicketDao;
import com.railwaycompany.persistence.dao.interfaces.TrainDao;
import com.railwaycompany.persistence.dao.interfaces.UserDao;
import com.railwaycompany.persistence.entities.Ticket;
import com.railwaycompany.persistence.entities.Train;
import com.railwaycompany.persistence.entities.User;
import com.railwaycompany.business.services.interfaces.TicketService;

public class TicketServiceImpl implements TicketService {

    private TicketDao ticketDao;
    private TrainDao trainDao;
    private UserDao userDao;

    public TicketServiceImpl(TicketDao ticketDao, TrainDao trainDao, UserDao userDao) {
        this.ticketDao = ticketDao;
        this.trainDao = trainDao;
        this.userDao = userDao;
    }

    @Override
    public boolean buyTicket(int userId, int trainId) {
        if (!ticketDao.isRegistered(userId, trainId)) {

            Train train = trainDao.read(trainId);
            User user = userDao.read(userId);

            if (train != null && user != null) {
                Ticket ticket = new Ticket();
                ticket.setTrain(train);
                ticket.setUser(user);
                ticketDao.create(ticket);
                return true;
            }
        }
        return false;
    }
}
