package com.railwaycompany.dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @param <T>
 */
public class HibernateDao<T extends Serializable> implements GenericDAO<T> {

    /**
     *
     */
    private static Logger log = Logger.getLogger(HibernateDao.class.getName());
    /**
     *
     */
    private EntityManager entityManager;
    /**
     *
     */
    private Class<T> entityClass;

    /**
     * @param entityClass
     */
    public HibernateDao(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    public HibernateDao(Class<T> entityClass) {
        this(EntityManagerSingleton.getInstance(), entityClass);
    }

    @Override
    public void create(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (EntityExistsException e) {
            log.log(Level.WARNING, "The entity already exists", e);
        } catch (IllegalArgumentException e) {
            log.log(Level.WARNING, "The instance is not an entity", e);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public T read(int key) {
        T instance = null;
        try {
            instance = entityManager.find(entityClass, key);
        } catch (IllegalArgumentException e) {
            log.log(Level.WARNING, "The first argument does not an entity entityClass or the second argument is not a" +
                    " " + "valid entityClass for that entity’s primary key or is null", e);
        }
        return instance;
    }

    @Override
    public T update(T entity) {
        T correspondingEntity = null;
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            correspondingEntity = entityManager.merge(entity);
            transaction.commit();
        } catch (IllegalArgumentException e) {
            log.log(Level.WARNING, "Instance is not an entity or is a removed entity", e);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return correspondingEntity;
    }

    @Override
    public void delete(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entity);
            transaction.commit();
        } catch (IllegalArgumentException e) {
            log.log(Level.WARNING, "Instance is not an entity or is a detached entity", e);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
}