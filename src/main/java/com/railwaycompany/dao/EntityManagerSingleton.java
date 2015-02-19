package com.railwaycompany.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerSingleton {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("RailwayInformationSystem");
    private static EntityManager entityManager;

    public static EntityManager getInstance() {
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    private EntityManagerSingleton() {
    }
}
