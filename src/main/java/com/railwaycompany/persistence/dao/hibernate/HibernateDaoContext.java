package com.railwaycompany.persistence.dao.hibernate;


import com.railwaycompany.persistence.dao.interfaces.DaoContext;
import com.railwaycompany.persistence.dao.interfaces.GenericDAO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HibernateDaoContext implements DaoContext {

    private Map<Class, GenericDAO> daoMap;

    public HibernateDaoContext() {
        daoMap = new ConcurrentHashMap<>();
    }

    @Override
    public boolean put(Class<? extends GenericDAO> clazz, GenericDAO dao) {
        if (!daoMap.containsKey(clazz)) {
            daoMap.put(clazz, dao);
            return true;
        }
        return false;
    }

    @Override
    public GenericDAO get(Class<? extends GenericDAO> clazz) {
        return daoMap.get(clazz);
    }

}
