package com.railwaycompany.services.servicesImpl;

import com.railwaycompany.dao.abstractDao.DaoFactory;
import com.railwaycompany.dao.abstractDao.TicketDao;
import com.railwaycompany.services.abstractServices.TicketService;

public class TicketServiceImpl implements TicketService {

    private TicketDao ticketDao;

    public TicketServiceImpl(DaoFactory daoFactory) {
        ticketDao = daoFactory.getTicketDao();
    }

    @Override
    public boolean buyTicket(int userId, int trainId) {
        return false;
    }
}
