package com.railwaycompany.entities;

import com.railwaycompany.dao.GenericDao;
import com.railwaycompany.dao.HibernateDao;

import java.util.Date;

public class EntityTestDrive {

    public static void main(String[] args) {

        GenericDao<User> userDao = new HibernateDao<>(User.class);

        User user = new User();

        user.setLogin("new_login");
        user.setPassword("password");
        user.setName("name");
        user.setSurname("surname");
        user.setEmployee(false);
        user.setBirthdate(new Date());

        userDao.create(user);

        User user2 = userDao.read(user.getId());
        if (user2 != null) {
            System.out.println("user: " + user2.getId() + " " + user2.getName() + " " + user2.getBirthdate());
        } else {
            System.out.println("User not found");
        }

        user.setName("new name");
        userDao.update(user);

        user2 = userDao.read(user.getId());
        if (user2 != null) {
            System.out.println("user: " + user2.getId() + " " + user2.getName() + " " + user2.getBirthdate());
        } else {
            System.out.println("User not found");
        }

        userDao.delete(user);

        user2 = userDao.read(user.getId());
        if (user2 != null) {
            System.out.println("user: " + user2.getId() + " " + user2.getName() + " " + user2.getBirthdate());
        } else {
            System.out.println("User not found");
        }
    }
}
