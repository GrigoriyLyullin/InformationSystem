package com.railwaycompany.persistence.dao.hibernate;

import com.railwaycompany.persistence.dao.interfaces.UserDao;
import com.railwaycompany.persistence.entities.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * HibernateDao implementation for work with User entities.
 */
@Repository
@Transactional
public class UserHibernateDao extends HibernateDao<User> implements UserDao {

    private static final Logger LOG = Logger.getLogger(UserHibernateDao.class.getName());

    private static final String FIND_USER_BY_LOGIN = "SELECT u FROM User u WHERE u.login = :login";

    /**
     * UserHibernateDao constructor.
     */
    public UserHibernateDao() {
        this.setEntityClass(User.class);
    }

    @Override
    public User findUser(String login) {
        Query queryFindUserStr = entityManager.createQuery(FIND_USER_BY_LOGIN);
        queryFindUserStr.setParameter("login", login);
        User user = null;
        try {
            user = (User) queryFindUserStr.getSingleResult();
        } catch (Exception e) {
            LOG.warn(e);
        }
        return user;
    }
}
