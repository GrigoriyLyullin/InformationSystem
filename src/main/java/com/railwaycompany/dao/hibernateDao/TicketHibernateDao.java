package com.railwaycompany.dao.hibernateDao;

import com.railwaycompany.dao.abstractDao.TicketDao;
import com.railwaycompany.entities.Ticket;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketHibernateDao extends HibernateDao<Ticket> implements TicketDao {

    private static final String TICKET_COUNT_BY_TRAIN_ID = "SELECT t FROM Ticket t WHERE t.train.id = :trainId";

    private static final String TICKET_BY_TRAIN_AND_USER_ID = "SELECT t FROM Ticket t WHERE t.train.id = :trainId " +
            "AND" + " t.user.id = :userId";

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
    public int count(int trainId) {

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
    public boolean isRegistered(int trainId, int userId) {

        Query query = entityManager.createQuery(TICKET_BY_TRAIN_AND_USER_ID);
        query.setParameter("trainId", trainId);
        query.setParameter("userId", userId);

        boolean isRegistered = false;
        try {
            Ticket ticket = (Ticket) query.getSingleResult();
            isRegistered = (ticket != null);
        } catch (NoResultException e) {
            log.log(Level.INFO, "No tickets was found for trainId: \"" + trainId + "\"");
        }
        return isRegistered;
    }
}
