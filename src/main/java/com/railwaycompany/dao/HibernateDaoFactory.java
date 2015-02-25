package com.railwaycompany.dao;

import javax.persistence.EntityManager;

/**
 * DaoFactory implementation for Hibernate.
 */
public class HibernateDaoFactory implements DaoFactory {

    /**
     * Interface used to interact with the persistence context.
     */
    private final EntityManager entityManager;

    /**
     * HibernateDaoFactory constructor.
     *
     * @param entityManager - interface used to interact with the persistence context.
     */
    public HibernateDaoFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public UserHibernateDao getUserHibernateDao() {
        return new UserHibernateDao(entityManager);
    }
}
