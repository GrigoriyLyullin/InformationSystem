package com.railwaycompany.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

public class HibernateDao<T extends Serializable> implements GenericDao<T> {

    private EntityManager entityManager = EntityManagerSingleton.getInstance();

    private Class<T> type;

    public HibernateDao(Class<T> type) {
        this.type = type;
    }

    @Override
    public void create(T t) {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(t);
        transaction.commit();

        if (transaction.isActive()) {
            transaction.rollback();
        }
    }

    @Override
    public T read(int key) {
        return entityManager.find(type, key);
    }

    @Override
    public void update(T t) {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(t);
        transaction.commit();

        if (transaction.isActive()) {
            transaction.rollback();
        }
    }

    @Override
    public void delete(T t) {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(t);
        transaction.commit();

        if (transaction.isActive()) {
            transaction.rollback();
        }

    }
}
