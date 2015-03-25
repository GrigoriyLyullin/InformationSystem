package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.UserData;
import com.railwaycompany.business.services.interfaces.UserService;
import com.railwaycompany.persistence.dao.interfaces.UserDao;
import com.railwaycompany.persistence.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private static final int USER_ID = 1;
    private static final int NOT_EXIST_USER_ID = 2;
    private static final String USER_LOGIN = "testUserLogin";
    private static final boolean USER_IS_EMPLOYEE = false;

    private UserService userService;

    @Before
    @Ignore
    public void setUp() throws Exception {

        User user = new User();
        user.setId(USER_ID);
        user.setLogin(USER_LOGIN);
//        user.setEmployee(USER_IS_EMPLOYEE);

        UserDao userDao = mock(UserDao.class);

        when(userDao.read(USER_ID)).thenReturn(user);
        when(userDao.read(NOT_EXIST_USER_ID)).thenReturn(null);

        userService = new UserServiceImpl();
    }

    @Test
    @Ignore
    public void testGetUserData() throws Exception {
        UserData userData = userService.getUserData(USER_ID);
        Assert.assertNotNull(userData);
        Assert.assertEquals(userData.getId(), USER_ID);
//        Assert.assertEquals(userData.getLogin(), USER_LOGIN);
//        Assert.assertEquals(userData.isEmployee(), USER_IS_EMPLOYEE);
    }

    @Test
    @Ignore
    public void testGetNotExistUserData() throws Exception {
        UserData userData = userService.getUserData(NOT_EXIST_USER_ID);
        Assert.assertNull(userData);
    }
}