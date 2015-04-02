package com.railwaycompany.persistence.dao.hibernate;

import com.railwaycompany.persistence.dao.interfaces.GenericDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * HibernateDao implementation of GenericDAO.
 *
 * @param <T> Entity type.
 */
@Repository
@Transactional
public class HibernateDao<T extends Serializable> implements GenericDAO<T> {

    /**
     * EntityManager, interface used to interact with the persistence context.
     */
    @Autowired
    protected EntityManager entityManager;

    /**
     * Entity class.
     */
    private Class<T> entityClass;

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void create(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public T read(int key) {
        return entityManager.find(entityClass, key);
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }
}
