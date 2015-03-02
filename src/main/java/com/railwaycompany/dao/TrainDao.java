package com.railwaycompany.dao;

import com.railwaycompany.entities.Train;

/**
 * GenericDao<Train> interface for work with Train entities.
 */
public interface TrainDao extends GenericDAO<Train> {

    /**
     * Finds Train entity by train number.
     *
     * @param number - train number
     * @return Train entity or null if train with this train number does not exist.
     */
    Train findTrain(int number);
}
