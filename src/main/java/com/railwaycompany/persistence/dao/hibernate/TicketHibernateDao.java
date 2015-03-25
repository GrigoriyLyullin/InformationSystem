package com.railwaycompany.persistence.dao.hibernate;

import com.railwaycompany.persistence.dao.interfaces.TicketDao;
import com.railwaycompany.persistence.entities.Ticket;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class TicketHibernateDao extends HibernateDao<Ticket> implements TicketDao {

    private static final String TICKET_COUNT_BY_TRAIN_ID = "SELECT t FROM Ticket t WHERE t.train.id = :trainId";

    private static final String TICKET_BY_TRAIN_AND_PASSENGER_ID = "SELECT t FROM Ticket t WHERE t.train.id = :trainId " +
            "AND" + " t.passenger.id = :passengerId";

    private static final String TICKET_BY_TRAIN_ID = "SELECT t FROM Ticket t WHERE t.train.id = :trainId";

    /**
     * Logger for StationHibernateDao class.
     */
    private static Logger log = Logger.getLogger(TicketHibernateDao.class.getName());

    /**
     * HibernateDao constructor.
     */
    public TicketHibernateDao() {
        this.setEntityClass(Ticket.class);
    }

    @Override
    public int countOfTickets(int trainId) {

        Query query = entityManager.createQuery(TICKET_COUNT_BY_TRAIN_ID);
        query.setParameter("trainId", trainId);

        int count = 0;
        try {
            count = query.getResultList().size();
        } catch (NoResultException e) {
            log.log(Level.INFO, "No tickets was found for trainId: \"" + trainId + "\"");
        }
        return count;
    }

    @Override
    public boolean hasBeenRegistered(int trainId, int passengerId) {

        Query query = entityManager.createQuery(TICKET_BY_TRAIN_AND_PASSENGER_ID);
        query.setParameter("trainId", trainId);
        query.setParameter("passengerId", passengerId);

        boolean registered = false;
        try {
            Ticket ticket = (Ticket) query.getSingleResult();
            registered = (ticket != null);
        } catch (NoResultException e) {
            log.log(Level.INFO, "No tickets was found for trainId: \"" + trainId + "\"");
        }
        return registered;
    }

    @Override
    public List<Ticket> getTicketsByTrainId(int trainId) {

        Query query = entityManager.createQuery(TICKET_BY_TRAIN_ID);
        query.setParameter("trainId", trainId);

        List<Ticket> ticketList = null;
        List resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            ticketList = new ArrayList<>();
            for (Object o : resultList) {
                if (o instanceof Ticket) {
                    ticketList.add((Ticket) o);
                }
            }
        }
        return ticketList;
    }
}
