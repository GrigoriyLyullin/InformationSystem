package com.railwaycompany.services.servicesImpl;

import com.railwaycompany.dao.abstractDao.DaoFactory;
import com.railwaycompany.dao.abstractDao.UserDao;
import com.railwaycompany.entities.User;
import com.railwaycompany.serviceBeans.UserData;
import com.railwaycompany.services.abstractServices.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(DaoFactory daoFactory) {
        this.userDao = daoFactory.getUserDao();
    }

    @Override
    public UserData getUserData(int userId) {
        UserData userData = null;
        User user = userDao.read(userId);
        if (user != null) {
            userData = new UserData();
            userData.setId(user.getId());
            userData.setName(user.getName());
            userData.setSurname(user.getSurname());
        }
        return userData;
    }
}
