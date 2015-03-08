package com.railwaycompany.persistence.dao.interfaces;

import com.railwaycompany.persistence.entities.Ticket;

/**
 * GenericDAO<Ticket> interface for work with Ticket entities.
 */
public interface TicketDao extends GenericDAO<Ticket> {

    /**
     * Counts the number of tickets that has been sold on a given train.
     *
     * @param trainId - train id
     * @return number of tickets that has been sold
     */
    int countOfTickets(int trainId);

    /**
     * Checks that passenger has been registered on the train.
     *
     * @param trainId     - train id
     * @param passengerId - user id
     * @return True if passenger has been registered on the train, otherwise - False
     */
    boolean hasBeenRegistered(int trainId, int passengerId);
}
