package com.railwaycompany.persistence.dao.jdbc;

import com.railwaycompany.persistence.dao.interfaces.GenericDAO;
import com.railwaycompany.persistence.dao.interfaces.UserDao;
import com.railwaycompany.persistence.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserDao implements GenericDAO<User>, UserDao {

    @Override
    public User findUser(String login) {
        return null;
    }

    @Override
    public void create(User entity) {

    }

    @Override
    public User read(int key) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public void delete(User entity) {

    }
}
