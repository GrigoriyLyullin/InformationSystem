package com.railwaycompany.services;

import com.railwaycompany.dao.DaoFactory;
import com.railwaycompany.dao.HibernateDaoFactorySingleton;
import com.railwaycompany.dao.UserDao;
import com.railwaycompany.entities.User;

import java.util.logging.Logger;

public class AuthenticationService {

    /**
     * Logger for UserService class.
     */
    private static Logger log = Logger.getLogger(AuthenticationService.class.getName());

    private DaoFactory daoFactory;

    private UserDao userDao;

    public AuthenticationService() {
        daoFactory = HibernateDaoFactorySingleton.getInstance();
        userDao = daoFactory.getUserDao();
    }

    public AuthenticationData signIn(String login, String password) {

        User user = userDao.findUser(login, password);

        AuthenticationData data = null;
        if (user != null) {
            data = new AuthenticationData();
            data.setId(user.getId());
            data.setPermission(user.isEmployee());
        }

        return data;
    }
}
