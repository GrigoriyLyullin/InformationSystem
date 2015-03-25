package com.railwaycompany.persistence.dao.hibernate;

import com.railwaycompany.persistence.dao.interfaces.UserDao;
import com.railwaycompany.persistence.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * HibernateDao implementation for work with User entities.
 */
@Repository
public class UserHibernateDao extends HibernateDao<User> implements UserDao {

    private static final String FIND_USER = "SELECT u FROM User u WHERE u.login = :login AND" + " u" +
            ".password = :password";
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
    public User findUser(String login, String password) {

        Query queryFindUserStr = entityManager.createQuery(FIND_USER);

        queryFindUserStr.setParameter("login", login);
        queryFindUserStr.setParameter("password", password);

        User user = null;
        try {
            user = (User) queryFindUserStr.getSingleResult();
        } catch (NoResultException e) {
            log.log(Level.INFO, "User was not found");
        }
        return user;
    }
}
