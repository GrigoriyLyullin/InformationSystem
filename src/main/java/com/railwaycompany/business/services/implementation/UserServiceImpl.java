package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.UserData;
import com.railwaycompany.business.services.interfaces.UserService;
import com.railwaycompany.persistence.dao.interfaces.UserDao;
import com.railwaycompany.persistence.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public UserServiceImpl() {
    }

    @Override
    public UserData getUserData(int userId) {
        UserData userData = null;
        User user = userDao.read(userId);
        if (user != null) {
            userData = new UserData();
            userData.setId(user.getId());
            userData.setUsername(user.getLogin());
            //TODO User role?
//            userData.setEmployee(user.isEmployee());
        }
        return userData;
    }

    @Override
    public Integer getUserIdByUsername(String username) {
        User user = userDao.findUser(username);
        if (user != null) {
            return user.getId();
        }
        return null;
    }
}
