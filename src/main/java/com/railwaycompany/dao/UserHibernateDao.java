package com.railwaycompany.dao;

import com.railwaycompany.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * HibernateDao implementation for work with User entities.
 */
public class UserHibernateDao extends HibernateDao<User> implements UserDao {

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

        Query queryFindUserStr = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login AND" + " u" +
                ".password = :password");

        queryFindUserStr.setParameter("login", login);
        queryFindUserStr.setParameter("password", password);

        Object result = queryFindUserStr.getSingleResult();

        if (result instanceof User) {
            return (User) result;
        }
        return null;
    }
}
