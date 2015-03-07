package com.railwaycompany.persistence.dao.hibernate;

import com.railwaycompany.persistence.dao.interfaces.UserDao;
import com.railwaycompany.persistence.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * HibernateDao implementation for work with User entities.
 */
public class UserHibernateDao extends HibernateDao<User> implements UserDao {

    /**
     * Logger for UserHibernateDao class.
     */
    private static Logger log = Logger.getLogger(UserHibernateDao.class.getName());

    private static final String FIND_USER = "SELECT u FROM User u WHERE u.login = :login AND" + " u" +
            ".password = :password";

    /**
     * UserHibernateDao constructor.
     *
     * @param entityManager - entity manager
     */
    public UserHibernateDao(EntityManager entityManager) {
        super(entityManager, User.class);
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
            log.log(Level.INFO, "No user was found for login: \"" + login + "\" and password: \"" + password + "\"");
        } catch (ClassCastException e) {
            log.log(Level.WARNING, "Query returns not User object.", e);
        }
        return user;
    }
}
