package com.railwaycompany.persistence.dao.interfaces;

import com.railwaycompany.persistence.entities.User;

/**
 * GenericDAO<User> interface for work with User entities.
 */
public interface UserDao extends GenericDAO<User> {

    /**
     * Find user by login and password.
     *
     * @param login - User login
     * @return User with this login and password or null if user with this parameters
     * does not exists in data source.
     */
    User findUser(String login);
}
