package com.railwaycompany.persistence.dao.jdbc;

import com.railwaycompany.persistence.dao.interfaces.GenericDAO;
import com.railwaycompany.persistence.dao.interfaces.TicketDao;
import com.railwaycompany.persistence.entities.Ticket;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcTicketDao implements GenericDAO<Ticket>, TicketDao {

    @Override
    public int countOfTickets(int trainId) {
        return 0;
    }

    @Override
    public boolean hasBeenRegistered(int trainId, int passengerId) {
        return false;
    }

    @Override
    public List<Ticket> getTicketsByTrainId(int trainId) {
        return null;
    }

    @Override
    public List<Ticket> getAll() {
        return null;
    }

    @Override
    public void create(Ticket entity) {

    }

    @Override
    public Ticket read(int key) {
        return null;
    }

    @Override
    public Ticket update(Ticket entity) {
        return null;
    }

    @Override
    public void delete(Ticket entity) {

    }
}
