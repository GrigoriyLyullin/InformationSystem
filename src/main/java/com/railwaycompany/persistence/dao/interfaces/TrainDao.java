package com.railwaycompany.persistence.dao.interfaces;

import com.railwaycompany.persistence.entities.Train;

/**
 * GenericDAO<Train> interface for work with Train entities.
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
