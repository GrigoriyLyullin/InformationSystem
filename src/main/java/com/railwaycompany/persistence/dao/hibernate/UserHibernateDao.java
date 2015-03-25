package com.railwaycompany.persistence.dao.hibernate;

import com.railwaycompany.persistence.dao.interfaces.UserDao;
import com.railwaycompany.persistence.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.logging.Logger;

/**
 * HibernateDao implementation for work with User entities.
 */
@Repository
public class UserHibernateDao extends HibernateDao<User> implements UserDao {

    private static final String FIND_USER_BY_LOGIN = "SELECT u FROM User u WHERE u.login = :login";
    /**
     * Logger for UserHibernateDao class.
     */
    private static Logger log = Logger.getLogger(UserHibernateDao.class.getName());

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
        return (User) queryFindUserStr.getSingleResult();
    }
}
