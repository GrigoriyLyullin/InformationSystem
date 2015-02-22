package com.railwaycompany.dao;

import org.junit.Test;

import java.io.Serializable;

public abstract class HibernateDaoTest<T extends Serializable> {

    protected static final String persistenceUnitName = "RailwayInformationSystemTest";
    protected static final long millisInDay = 86400000L;
    protected Class<T> entityClass;
    protected GenericDAO<T> dao;

    @Test
    public abstract void testCreate() throws Exception;

    @Test
    public abstract void testRead() throws Exception;

    @Test
    public abstract void testUpdate() throws Exception;

    @Test
    public abstract void testDelete() throws Exception;
}
