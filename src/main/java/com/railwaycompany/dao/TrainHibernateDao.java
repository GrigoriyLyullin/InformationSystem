package com.railwaycompany.dao;

import com.railwaycompany.entities.Train;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrainHibernateDao extends HibernateDao<Train> implements TrainDao {

    private static final String FIND_TRAIN_BY_NUMBER = "SELECT t FROM Train t WHERE t.number = :number";

    /**
     * Logger for StationHibernateDao class.
     */
    private static Logger log = Logger.getLogger(TrainHibernateDao.class.getName());

    /**
     * HibernateDao constructor.
     *
     * @param entityManager - entity manager
     */
    public TrainHibernateDao(EntityManager entityManager) {
        super(entityManager, Train.class);
    }


    @Override
    public Train findTrain(int number) {

        Query query = entityManager.createQuery(FIND_TRAIN_BY_NUMBER);
        query.setParameter("number", number);

        Train train = null;
        try {
            train = (Train) query.getSingleResult();
        } catch (NoResultException e) {
            log.log(Level.INFO, "No train was found for number: \"" + number + "\"");
        } catch (ClassCastException e) {
            log.log(Level.WARNING, "Query returns not Train object.", e);
        }

        return train;
    }
}
