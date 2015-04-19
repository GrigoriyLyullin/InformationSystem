package com.railwaycompany.persistence.dao.jdbc;

import com.railwaycompany.persistence.dao.interfaces.GenericDAO;
import com.railwaycompany.persistence.dao.interfaces.TrainDao;
import com.railwaycompany.persistence.entities.Train;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcTrainDao implements GenericDAO<Train>, TrainDao {

    @Override
    public List<Train> getAll() {
        return null;
    }

    @Override
    public List<Train> findTrains(int trainNumber, int trainSeats) {
        return null;
    }

    @Override
    public void create(Train entity) {

    }

    @Override
    public Train read(int key) {
        return null;
    }

    @Override
    public Train update(Train entity) {
        return null;
    }

    @Override
    public void delete(Train entity) {

    }
}
