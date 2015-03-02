package com.railwaycompany.dao;

import com.railwaycompany.entities.Train;

import javax.persistence.EntityManager;

public class TrainHibernateDao extends HibernateDao<Train> implements TrainDao {

    /**
     * HibernateDao constructor.
     *
     * @param entityManager - entity manager
     */
    public TrainHibernateDao(EntityManager entityManager) {
        super(entityManager, Train.class);
    }

   
}
