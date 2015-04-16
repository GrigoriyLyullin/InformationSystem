package com.railwaycompany.persistence.dao.jdbc;

import com.railwaycompany.persistence.dao.interfaces.GenericDAO;
import com.railwaycompany.persistence.dao.interfaces.StationDao;
import com.railwaycompany.persistence.entities.Station;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcStationDao implements GenericDAO<Station>, StationDao {

    @Override
    public Station getStation(String name) {
        return null;
    }

    @Override
    public List<Station> getAll() {
        return null;
    }

    @Override
    public void create(Station entity) {

    }

    @Override
    public Station read(int key) {
        return null;
    }

    @Override
    public Station update(Station entity) {
        return null;
    }

    @Override
    public void delete(Station entity) {

    }
}
