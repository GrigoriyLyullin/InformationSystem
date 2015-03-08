package com.railwaycompany.persistence.dao.interfaces;

import com.railwaycompany.persistence.entities.Passenger;

import java.util.Date;

/**
 * GenericDAO<Passenger> interface for work with User entities.
 */
public interface PassengerDao extends GenericDAO<Passenger> {

    Passenger getPassenger(String name, String surname, Date birthdate);
}
