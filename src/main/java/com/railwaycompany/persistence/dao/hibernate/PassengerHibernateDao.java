package com.railwaycompany.persistence.dao.hibernate;

import com.railwaycompany.persistence.dao.interfaces.PassengerDao;
import com.railwaycompany.persistence.entities.Passenger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.util.Date;

public class PassengerHibernateDao extends HibernateDao<Passenger> implements PassengerDao {

    private static final String FIND_PASSENGER = "SELECT p FROM Passenger p WHERE p.name = :name " +
            "AND p.surname = :surname AND p.birthdate = :birthdate";

    /**
     * HibernateDao constructor.
     *
     * @param entityManager
     */
    public PassengerHibernateDao(EntityManager entityManager) {
        super(entityManager, Passenger.class);
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
        } catch (NoResultException e) {

        } catch (NonUniqueResultException e) {

        }

        return passenger;
    }
}
