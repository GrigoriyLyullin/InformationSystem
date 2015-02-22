package com.railwaycompany.dao;

import java.io.Serializable;

public interface GenericDAO<T extends Serializable> {

    /**
     * Create an instance in the data source.
     *
     * @param entity - entity instance
     */
    void create(T entity);

    /**
     * Find an instance in the data source by primary key.
     *
     * @param key - primary key
     * @return the found entity instance or null if the entity does not exist
     */
    T read(int key);

    /**
     * Update the state of the corresponding entity by the given entity.
     *
     * @param entity - entity instance
     * @return the instance that the state was updated to or null if instance is a removed entity
     */
    T update(T entity);

    /**
     * Remove the entity instance.
     *
     * @param entity - entity instance
     */
    void delete(T entity);
}