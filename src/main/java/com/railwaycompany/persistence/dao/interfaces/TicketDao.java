package com.railwaycompany.persistence.dao.interfaces;

import com.railwaycompany.persistence.entities.Ticket;

import java.util.List;

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

    /**
     * Uses to obtain a list of tickets sold on the train with this train id.
     *
     * @param trainId - train id
     * @return List of tickets
     */
    List<Ticket> getTicketsByTrainId(int trainId);
}
