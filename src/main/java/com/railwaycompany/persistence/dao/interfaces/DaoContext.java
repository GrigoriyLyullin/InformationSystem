package com.railwaycompany.persistence.dao.interfaces;

public interface DaoContext {

    boolean put(Class<? extends GenericDAO> clazz, GenericDAO dao);

    GenericDAO get(Class<? extends GenericDAO> clazz);

}
