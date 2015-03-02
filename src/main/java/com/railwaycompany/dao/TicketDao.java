package com.railwaycompany.dao;

import com.railwaycompany.entities.Ticket;

/**
 * GenericDao<Ticket> interface for work with Ticket entities.
 */
public interface TicketDao extends GenericDAO<Ticket> {

    /**
     * Counts the number of tickets that has been sold on a given train.
     *
     * @param trainId - train id
     * @return number of tickets that has been sold
     */
    int count(int trainId);

    /**
     * Checks that user has been registered on the train.
     *
     * @param trainId - train id
     * @param userId - user id
     * @return True if user has been registered on the train, otherwise - False
     */
    boolean isRegistered(int trainId, int userId);
}
