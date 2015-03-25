package com.railwaycompany.persistence.dao.hibernate;

import com.railwaycompany.persistence.dao.interfaces.GenericDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * HibernateDao implementation of GenericDAO.
 *
 * @param <T> Entity type.
 */
@Repository
public class HibernateDao<T extends Serializable> implements GenericDAO<T> {

    /**
     * Logger for HibernateDao class.
     */
    private static Logger log = Logger.getLogger(HibernateDao.class.getName());

    /**
     * EntityManager, interface used to interact with the persistence context.
     */
    @Autowired
    protected EntityManager entityManager;

    /**
     * Entity class.
     */
    private Class<T> entityClass;

    /**
     * HibernateDao constructor.
     *
     */
//    public HibernateDao(EntityManager entityManager, Class<T> entityClass) {
//        this.entityManager = entityManager;
//        this.entityClass = entityClass;
//    }
    public HibernateDao() {
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void create(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public T read(int key) {
        return entityManager.find(entityClass, key);
    }

    @Override
    public T update(T entity) {
        T correspondingEntity = null;
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            correspondingEntity = entityManager.merge(entity);
            transaction.commit();
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
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
}
