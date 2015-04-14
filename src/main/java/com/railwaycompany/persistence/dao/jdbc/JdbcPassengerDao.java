package com.railwaycompany.persistence.dao.jdbc;

import com.railwaycompany.persistence.dao.interfaces.GenericDAO;
import com.railwaycompany.persistence.dao.interfaces.PassengerDao;
import com.railwaycompany.persistence.entities.Passenger;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class JdbcPassengerDao implements GenericDAO<Passenger>, PassengerDao {

    private static final Logger LOG = Logger.getLogger(JdbcPassengerDao.class.getName());

    @Autowired
    private JdbcConnection connection;

    @Override
    public Passenger getPassenger(String name, String surname, Date birthdate) {
        LOG.warn("Unsupported method getPassenger(String name, String surname, Date birthdate)");
        return null;
    }

    @Override
    public void create(Passenger passenger) {
        LOG.warn("Unsupported method create(Passenger passenger)");
    }

    @Override
    public Passenger read(int key) {
        LOG.warn("Unsupported method Passenger read(int key)");
        return null;
    }

    @Override
    public Passenger update(Passenger passenger) {
        LOG.warn("Unsupported method update(Passenger passenger)");
        return null;
    }

    @Override
    public void delete(Passenger passenger) {
        LOG.warn("Unsupported method delete(Passenger passenger)");
    }
}
