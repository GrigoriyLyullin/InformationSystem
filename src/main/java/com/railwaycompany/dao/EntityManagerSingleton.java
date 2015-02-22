package com.railwaycompany.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerSingleton {

    private static final String persistenceUnitName = "RailwayInformationSystem";
    private static EntityManagerFactory factory;
    private static EntityManager entityManager;

    public static EntityManager getInstance() {
        if (entityManager == null) {
            factory = Persistence.createEntityManagerFactory(persistenceUnitName);
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public static EntityManager getInstance(String persistenceUnitName) {
        if (entityManager == null) {
            factory = Persistence.createEntityManagerFactory(persistenceUnitName);
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    private EntityManagerSingleton() {
    }
}
