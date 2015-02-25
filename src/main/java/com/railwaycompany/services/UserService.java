package com.railwaycompany.services;

import com.railwaycompany.dao.DaoFactory;
import com.railwaycompany.dao.HibernateDaoFactory;
import com.railwaycompany.dao.UserHibernateDao;
import com.railwaycompany.entities.User;

public class UserService {

    private DaoFactory daoFactory;

    private UserHibernateDao userHibernateDao;

    public UserService() {
        daoFactory = new HibernateDaoFactory();
        userHibernateDao = daoFactory.getUserHibernateDao();
    }

    public String signin(String login, String password) {

        User user = userHibernateDao.findUser(login, password);

        String page = "User page for: " + user.getId() + " " + user.getName() + " " + user.getSurname();

        return page;
    }
}
