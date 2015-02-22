package com.railwaycompany.dao;

import java.io.Serializable;

public abstract class HibernateDaoTest<T extends Serializable> {

    protected Class<T> entityClass;
    protected GenericDAO<T> dao;

}
