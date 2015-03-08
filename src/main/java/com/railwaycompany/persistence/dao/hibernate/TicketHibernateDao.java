package com.railwaycompany.persistence.dao.hibernate;

import com.railwaycompany.persistence.dao.interfaces.TicketDao;
import com.railwaycompany.persistence.entities.Ticket;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketHibernateDao extends HibernateDao<Ticket> implements TicketDao {

    private static final String TICKET_COUNT_BY_TRAIN_ID = "SELECT t FROM Ticket t WHERE t.train.id = :trainId";

    private static final String TICKET_BY_TRAIN_AND_PASSENGER_ID = "SELECT t FROM Ticket t WHERE t.train.id = :trainId " +
            "AND" + " t.passenger.id = :passengerId";

//    private static final String TICKET_BY_TRAIN_ID = "SELECT t FROM Ticket t WHERE t.id = :ticketId AND t.passenger.id = :passengerId";

    /**
     * Logger for StationHibernateDao class.
     */
    private static Logger log = Logger.getLogger(TicketHibernateDao.class.getName());

    /**
     * HibernateDao constructor.
     *
     * @param entityManager - entity manager
     */
    public TicketHibernateDao(EntityManager entityManager) {
        super(entityManager, Ticket.class);
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
}
