package com.railwaycompany.services;

import com.railwaycompany.dao.DaoFactory;
import com.railwaycompany.dao.HibernateDaoFactorySingleton;
import com.railwaycompany.dao.UserDao;
import com.railwaycompany.entities.User;

import java.util.logging.Logger;

public class UserService {

    /**
     * Logger for UserService class.
     */
    private static Logger log = Logger.getLogger(UserService.class.getName());

    private DaoFactory daoFactory;

    private UserDao userDao;

    public UserService() {
        daoFactory = HibernateDaoFactorySingleton.getInstance();
        userDao = daoFactory.getUserDao();
    }

    public String signin(String login, String password) {

        User user = userDao.findUser(login, password);

        String page = null;
        if (user != null) {
            page = "User page for: " + user.getId() + " " + user.getName() + " " + user.getSurname();
        }

        return page;
    }
}
