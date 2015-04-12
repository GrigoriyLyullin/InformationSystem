package com.railwaycompany.persistence.dao.hibernate;

import com.railwaycompany.persistence.dao.interfaces.PassengerDao;
import com.railwaycompany.persistence.entities.Passenger;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.util.Date;

@Repository
public class PassengerHibernateDao extends HibernateDao<Passenger> implements PassengerDao {

    private static final Logger LOG = Logger.getLogger(PassengerHibernateDao.class.getName());

    private static final String FIND_PASSENGER = "SELECT p FROM Passenger p WHERE p.name = :name " +
            "AND p.surname = :surname AND p.birthdate = :birthdate";

    /**
     * HibernateDao constructor.
     */
    public PassengerHibernateDao() {
        this.setEntityClass(Passenger.class);
    }


    @Override
    public Passenger getPassenger(String name, String surname, Date birthdate) {

        Query query = entityManager.createQuery(FIND_PASSENGER);
        query.setParameter("name", name);
        query.setParameter("surname", surname);
        query.setParameter("birthdate", birthdate);

        Passenger passenger = null;
        try {
            passenger = (Passenger) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            LOG.warn(e);
        }
        return passenger;
    }
}
