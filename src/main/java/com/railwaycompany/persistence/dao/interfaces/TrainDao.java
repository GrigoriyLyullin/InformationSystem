package com.railwaycompany.persistence.dao.interfaces;

import com.railwaycompany.persistence.entities.Train;

import java.util.List;

/**
 * GenericDAO<Train> interface for work with Train entities.
 */
public interface TrainDao extends GenericDAO<Train> {

    List<Train> getAll();

    List<Train> findTrains(int trainNumber, int trainSeats);
}
