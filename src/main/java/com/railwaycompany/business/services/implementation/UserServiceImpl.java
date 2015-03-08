package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.UserData;
import com.railwaycompany.business.services.interfaces.UserService;
import com.railwaycompany.persistence.dao.interfaces.DaoContext;
import com.railwaycompany.persistence.dao.interfaces.UserDao;
import com.railwaycompany.persistence.entities.User;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(DaoContext daoContext) {
        userDao = (UserDao) daoContext.get(UserDao.class);
    }

    @Override
    public UserData getUserData(int userId) {
        UserData userData = null;
        User user = userDao.read(userId);
        if (user != null) {
            userData = new UserData();
            userData.setId(user.getId());
            userData.setLogin(user.getLogin());
        }
        return userData;
    }
}
