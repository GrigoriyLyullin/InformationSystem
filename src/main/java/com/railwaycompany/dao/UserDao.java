package com.railwaycompany.dao;

import com.railwaycompany.entities.User;

/**
 * GenericDao<User> interface for work with User entities.
 */
public interface UserDao extends GenericDAO<User> {

    /**
     * Find user by username and password.
     *
     * @param username - User login
     * @param password - User password
     * @return User with this username and password or null if user with this parameters does not exists in data source.
     */
    User findUser(String username, String password);

}
