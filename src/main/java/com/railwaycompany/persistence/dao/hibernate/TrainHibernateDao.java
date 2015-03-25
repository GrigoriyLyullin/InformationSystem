package com.railwaycompany.persistence.dao.hibernate;

import com.railwaycompany.persistence.dao.interfaces.TrainDao;
import com.railwaycompany.persistence.entities.Train;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class TrainHibernateDao extends HibernateDao<Train> implements TrainDao {

    private static final String FIND_TRAIN_BY_NUMBER_AND_SEATS = "SELECT t FROM Train t WHERE t.number = :number " +
            "AND t.seats = :seats";
    private static final String SELECT_ALL_TRAINS = "SELECT t FROM Train t";

    /**
     * Logger for StationHibernateDao class.
     */
    private static Logger log = Logger.getLogger(TrainHibernateDao.class.getName());

    /**
     * HibernateDao constructor.
     */
    public TrainHibernateDao() {
        this.setEntityClass(Train.class);
    }


    @Override
    public List<Train> getAll() {
        Query query = entityManager.createQuery(SELECT_ALL_TRAINS);
        List<Train> trainList = null;
        List resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            trainList = new ArrayList<>();
            for (Object o : resultList) {
                if (o instanceof Train) {
                    trainList.add((Train) o);
                }
            }
        }
        return trainList;
    }

    @Override
    public List<Train> findTrains(int trainNumber, int trainSeats) {
        Query query = entityManager.createQuery(FIND_TRAIN_BY_NUMBER_AND_SEATS);
        query.setParameter("number", trainNumber);
        query.setParameter("seats", trainSeats);
        List<Train> trainList = null;
        List resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            trainList = new ArrayList<>();
            for (Object o : resultList) {
                if (o instanceof Train) {
                    trainList.add((Train) o);
                }
            }
        }
        return trainList;
    }
}
