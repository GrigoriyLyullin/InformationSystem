package com.railwaycompany.dao;

import com.railwaycompany.entities.User;
import org.junit.Assert;
import org.junit.Before;

import java.util.Date;

public class HibernateDaoTestUser extends HibernateDaoTest<User> {

    private static final long millisInDay = 86400000L;
    private User testUser;

    @Before
    public void setUp() throws Exception {
        entityClass = User.class;
        dao = new HibernateDao<>(EntityManagerSingleton.getInstance(persistenceUnitName), entityClass);

        Date currentDate = new Date();
        testUser = new User();
        testUser.setLogin("testLogin" + currentDate.getTime());
        testUser.setPassword("testPassword" + currentDate.getTime());
        testUser.setName("testName" + currentDate.getTime());
        testUser.setSurname("testSurname" + currentDate.getTime());
        testUser.setEmployee(false);
        testUser.setBirthdate(new Date());
    }


    @Override
    public void testCreate() throws Exception {
        dao.create(testUser);
        User createdUser = dao.read(testUser.getId());
        Assert.assertNotNull(createdUser);
        Assert.assertNotNull(createdUser.getId());
        dao.delete(testUser);
    }

    @Override
    public void testRead() throws Exception {
        dao.create(testUser);
        int id = testUser.getId();
        User readUser = dao.read(id);
        Assert.assertNotNull(readUser);
        dao.delete(testUser);
        readUser = dao.read(id);
        Assert.assertNull(readUser);
    }

    @Override
    public void testUpdate() throws Exception {

        dao.create(testUser);

        int id = testUser.getId();
        String login = testUser.getLogin();
        String password = testUser.getPassword();
        String name = testUser.getName();
        String surname = testUser.getSurname();
        boolean employee = testUser.isEmployee();
        Date birthdate = testUser.getBirthdate();

        testUser.setLogin("updated " + login);
        testUser.setPassword("updated " + password);
        testUser.setName("updated " + name);
        testUser.setSurname("updated " + surname);
        testUser.setEmployee(!employee);
        Date newDate = new Date(birthdate.getTime() - millisInDay);
        testUser.setBirthdate(newDate);

        dao.update(testUser);

        User updatedUser = dao.read(id);

        Assert.assertNotEquals(login, updatedUser.getLogin());
        Assert.assertNotEquals(password, updatedUser.getPassword());
        Assert.assertNotEquals(name, updatedUser.getName());
        Assert.assertNotEquals(surname, updatedUser.getSurname());
        Assert.assertNotEquals(employee, updatedUser.isEmployee());
        Assert.assertNotEquals(birthdate, updatedUser.getBirthdate());

        dao.delete(testUser);
    }

    @Override
    public void testDelete() throws Exception {
        dao.create(testUser);
        int id = testUser.getId();
        Assert.assertNotNull(dao.read(id));
        dao.delete(testUser);
        Assert.assertNull(dao.read(id));
    }
}